package shparos.payment.presentation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shparos.payment.dto.PaymentRequest;
import shparos.payment.application.PaymentService;

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

}
