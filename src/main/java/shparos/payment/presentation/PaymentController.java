package shparos.payment.presentation;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shparos.payment.dto.FinishSettlementRequest;
import shparos.payment.dto.PaymentRequest;
import shparos.payment.application.PaymentService;
import shparos.payment.dto.PaymentResultResponseList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    // 결제완료,취소된 데이터 저장
    @PostMapping("/save")
    private ResponseEntity<String> savePayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.savePayment(paymentRequest);
        return ResponseEntity.ok("결제 데이터 저장");
    }


    // 결제완료,취소 건들의 리스트 조회
    @GetMapping("/list")
    private List<PaymentResultResponseList> getNormalStatus() {
        return paymentService.getPaymentsList();
    }

    // 정산 완료시 상태 변경
    @PostMapping("/finish-settlement")
    private void finishSettlement(@RequestBody FinishSettlementRequest finishSettlementRequest) {
        paymentService.finishSettlement(finishSettlementRequest);
    }
}
