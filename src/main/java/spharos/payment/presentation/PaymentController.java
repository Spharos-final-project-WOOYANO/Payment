package spharos.payment.presentation;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spharos.payment.global.common.response.BaseResponse;
import spharos.payment.application.PaymentService;
import spharos.payment.batch.BatchScheduler;
import spharos.payment.dto.PaymentRequest;


import spharos.payment.dto.PaymentResultResponse;
import spharos.payment.scheduler.PaymentListScheduler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    private final PaymentListScheduler paymentListScheduler;
    private final BatchScheduler batchScheduler;


    @Operation(summary = "결제 승인",
            description = "토스 api로 통신")
    @GetMapping("/success")
    public BaseResponse<?> reservationApproveService(@RequestParam(name = "serviceId") Long serviceId,
                                                     @RequestParam(name = "workerId") Long workerId,
                                                     @RequestParam(name = "userEmail") String userEmail,
                                                     @RequestParam(name = "reservationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate reservationDate,
                                                     @RequestParam(name = "request") String request,
                                                     @RequestParam(name = "address") String address,
                                                     @RequestParam(name = "clientEmail") String clientEmail,
                                                     @RequestParam(name = "orderId") String orderId,
                                                     @RequestParam(name = "paymentKey") String paymentKey,
                                                     @RequestParam(name = "amount") int amount,
                                                     @RequestParam(name = "serviceStart") @DateTimeFormat(pattern = "HH:mm") LocalTime serviceStart,
                                                     @RequestParam(name = "reservationGoodsId") List<Long> reservationGoodsId) {

        paymentService.approvePayment(paymentKey, orderId, amount, serviceId, workerId, userEmail,
                reservationDate, request, address, clientEmail, serviceStart, reservationGoodsId);

        //return ResponseEntity.ok(paymentResponse);
        return new BaseResponse<>();
    }




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


    //결제 내역
    @GetMapping("/test")
    public String test() throws JsonProcessingException {
        paymentListScheduler.sendMonthlyPaymentEvent();
       return "Test";
    }

    //batch test
    @GetMapping("/batch")
    public void batchTest()  {
        //paymentService.batchTest();

        batchScheduler.runJob();

    }



}
