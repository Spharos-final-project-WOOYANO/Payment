package spharos.payment.presentation;


import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spharos.payment.dto.FinishSettlementRequest;
import spharos.payment.dto.PaymentRequest;
import spharos.payment.application.PaymentService;
import spharos.payment.dto.PaymentResultResponse;
import spharos.payment.scheduler.PaymentListScheduler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentListScheduler paymentListScheduler;



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

    // 정산 완료시 상태 변경
    @PostMapping("/finish-settlement")
    public void finishSettlement(@RequestBody FinishSettlementRequest finishSettlementRequest) {
        paymentService.finishSettlement(finishSettlementRequest);
    }

    //kafkatest
    @GetMapping("/test")
    public String test() throws JsonProcessingException {
        paymentListScheduler.sendMonthlyPaymentEvent();
       return "Test";
    }
}