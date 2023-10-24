package shparos.payment.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shparos.payment.domain.Payment;
import shparos.payment.dto.PaymentRequest;
import shparos.payment.repository.PaymentRepository;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void savePayment(PaymentRequest paymentRequest) {
        Payment payment = Payment.createPayment(paymentRequest.getClientEmail(), paymentRequest.getPaymentKey(),
                paymentRequest.getOrderId()
                , paymentRequest.getOrderName(), paymentRequest.getPayType(), paymentRequest.getTotalAmount(),
                paymentRequest.getRequestedAt(), paymentRequest.getApprovedAt(),paymentRequest.getPayStatus());
        paymentRepository.save(payment);
    }

}
