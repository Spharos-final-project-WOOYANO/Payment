package spharos.payment.axon.agegregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import spharos.payment.axon.command.SavePaymentCommand;
import spharos.payment.axon.event.PaymentSaveEvent;
import spharos.payment.domain.PaymentStatus;
import spharos.payment.domain.PaymentType;


@Aggregate
@NoArgsConstructor
@Slf4j
public class PaymentAggregate {

    @AggregateIdentifier
    private String id;


    @CommandHandler
    public PaymentAggregate(SavePaymentCommand command)  {
     //   throw new RuntimeException("Custom runtime exception message");
        log.info("getReservation_num = " + command.getReservation_num());
        PaymentSaveEvent reservationCreateEvent = new PaymentSaveEvent(command.getReservation_num(), command.getClientEmail(),
                command.getPaymentType(), command.getTotalAmount(), command.getApprovedAt(), command.getPaymentStatus());

        apply(reservationCreateEvent);

    }

    @EventSourcingHandler
    public void on(PaymentSaveEvent event) {
        log.info("eventsoure");
        this.id = event.getReservation_num();
    }

}
