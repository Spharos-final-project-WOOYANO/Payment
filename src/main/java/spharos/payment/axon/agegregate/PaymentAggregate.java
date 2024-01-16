package spharos.payment.axon.agegregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import spharos.payment.axon.command.CancelPaymentCommand;
import spharos.payment.axon.command.SavePaymentCommand;
import spharos.payment.axon.event.PaymentSaveEvent;


@Aggregate
@NoArgsConstructor
@Slf4j
public class PaymentAggregate {

    @AggregateIdentifier
    private String orderId;


    // 결제 저장
    @CommandHandler
    public PaymentAggregate(SavePaymentCommand command)  {
        //throw new RuntimeException("Custom runtime exception message");
        log.info("getReservation_num = " + command.getOrderId());
        PaymentSaveEvent reservationCreateEvent = new PaymentSaveEvent(command.getOrderId(), command.getAmount(),
                command.getClientEmail(), command.getPaymentKey(), command.getSuppliedAmount(), command.getVat(),
                command.getStatus(),command.getMethod(),command.getApprovedAt(),command.getServiceId(),command.getWorkerId(),
                command.getReservationDate(),command.getRequest(),command.getAddress(),command.getServiceStart()
                ,command.getReservationGoodsId(),command.getUserEmail());

        apply(reservationCreateEvent);
    }

    @CommandHandler
    public void cancelCommand(CancelPaymentCommand command){
        CancelPaymentCommand cancelPaymentCommand = new CancelPaymentCommand(command.getOrderId(),
                command.getPaymentKey());
        apply(cancelPaymentCommand);

    }



    @EventSourcingHandler
    public void on(PaymentSaveEvent event) {
        this.orderId = event.getOrderId();
    }


    @EventSourcingHandler
    public void cancel(CancelPaymentCommand event) {
        this.orderId = event.getOrderId();
    }
}
