package spharos.payment.axon.event.handle;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.AllowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Component;
import spharos.payment.axon.event.PaymentSaveEvent;
import spharos.payment.domain.Payment;
import spharos.payment.domain.PaymentType;
import spharos.payment.infrastructure.PaymentRepository;

@Component
@Slf4j
@EnableRetry
@RequiredArgsConstructor
public class ReservationEventHandler {
    private final PaymentRepository paymentRepository;


    @EventHandler
    @AllowReplay
    public void on(PaymentSaveEvent event) {
        PaymentType paymentType = event.getPaymentType();

        Payment payment = Payment.createPayment(event.getClientEmail(), event.getPaymentType(), event.getTotalAmount(),
                event.getApprovedAt(), event.getPaymentStatus());
        paymentRepository.save(payment);

    }


}

