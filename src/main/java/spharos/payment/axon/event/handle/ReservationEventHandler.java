package spharos.payment.axon.event.handle;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Component;
import spharos.payment.axon.event.PaymentSaveEvent;
import spharos.payment.domain.Payment;
import spharos.payment.infrastructure.PaymentRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationEventHandler {
    private final PaymentRepository paymentRepository;


    @EventHandler
    public void on(PaymentSaveEvent event) {
        Payment payment = Payment.createPayment(event.getClientEmail(), event.getPaymentType(), event.getTotalAmount(),
                event.getApprovedAt(), event.getPaymentStatus());
        paymentRepository.save(payment);

    }


}

