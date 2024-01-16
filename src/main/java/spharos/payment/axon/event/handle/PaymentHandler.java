package spharos.payment.axon.event.handle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import spharos.payment.axon.command.CancelPaymentCommand;
import spharos.payment.axon.event.PaymentCancelEvent;
import spharos.payment.axon.event.PaymentSaveEvent;
import spharos.payment.domain.Payment;
import spharos.payment.domain.enumPackage.PaymentMethod;
import spharos.payment.domain.enumPackage.PaymentStatus;

import spharos.payment.infrastructure.PaymentRepository;

@Component
@Slf4j
@RequiredArgsConstructor
@ProcessingGroup("payE")
public class PaymentHandler {
    private final PaymentRepository paymentRepository;


    @EventHandler
    public void on(PaymentSaveEvent event) {

        PaymentMethod paymentMethod = PaymentMethod.findByValue(event.getMethod());
        PaymentStatus paymentStatus = PaymentStatus.findByValue(event.getStatus());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        LocalDateTime approvedAt = LocalDateTime.parse(event.getApprovedAt(), formatter);

        Payment payment = Payment.createPayment(event.getClientEmail(), paymentMethod, event.getAmount(), paymentStatus,
                event.getOrderId(),event.getPaymentKey(),event.getSuppliedAmount(), event.getVat(), approvedAt);
        paymentRepository.save(payment);

    }

    @EventHandler
    public void cancel(PaymentCancelEvent event){
        paymentRepository.deleteByPaymentKey(event.getPaymentKey());
    }

}

