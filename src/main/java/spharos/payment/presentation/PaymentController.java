package spharos.payment.presentation;


import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spharos.payment.batch.BatchScheduler;
import spharos.payment.domain.Payment;
import spharos.payment.dto.FinishSettlementRequest;
import spharos.payment.dto.PaymentRequest;
import spharos.payment.application.PaymentServiceImpl;


import spharos.payment.dto.PaymentResultResponse;
import spharos.payment.scheduler.PaymentListScheduler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@Slf4j
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    private final PaymentListScheduler paymentListScheduler;
    private final BatchScheduler batchScheduler;


    // 결제완료,취소된 데이터 저장
    @PostMapping("/save")
    public ResponseEntity<String> savePayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.savePayment(paymentRequest);
        return ResponseEntity.ok("결제 데이터 저장");
    }


    // 결제완료,취소 건들의 리스트 조회
    @GetMapping("/list")
    public List<PaymentResultResponse> getCompletedAndCancelledTransactions() {
        return paymentService.getPaymentsList();
    }


    //kafkatest
    @GetMapping("/test")
    public String test() throws JsonProcessingException {
        paymentListScheduler.sendMonthlyPaymentEvent();
       return "Test";
    }

    //batch test
    @GetMapping("/batch")
    public List<Payment> batchTest()  {
        List<Payment> payments = paymentService.batchTest();
        return payments;
        // batchScheduler.runJob();

    }


}
