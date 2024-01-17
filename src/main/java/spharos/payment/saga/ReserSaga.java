package spharos.payment.saga;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spharos.payment.application.TossPaymentAccept;
import spharos.payment.axon.command.CancelPaymentCommand;
import spharos.payment.axon.event.PaymentSaveEvent;
import spharos.reservation.reservations.axon.command.CreateReservationCommand;

@Slf4j
@Saga
public class ReserSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private TossPaymentAccept tossPaymentAccept;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(PaymentSaveEvent event) {

        log.info("[StartSaga] saga");
        // on동작에서 associationProperty 설정한거에서 핸들작업 쭉 과정에서 하나로 이어주는게 associationProperty
        //앞으로의 핸들 동작을 위해서 어떤것을 associationProperty 정의
       // SagaLifecycle.associateWith("reservation_num", event.getReservation_num());

        String stringApprovedAt = event.getApprovedAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        LocalDateTime approvedAt = LocalDateTime.parse(stringApprovedAt, formatter);
        commandGateway.send(new CreateReservationCommand(event.getOrderId(),event.getAmount(),event.getServiceId(),
                event.getUserEmail(),event.getReservationDate(),event.getRequest(),event.getAddress(),event.getServiceStart()
        ,event.getWorkerId(),event.getReservationGoodsId(),approvedAt), new CommandCallback<CreateReservationCommand, Object>() {
            @Override
            public void onResult(CommandMessage<? extends CreateReservationCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {
                if(commandResultMessage.isExceptional()){

                    // 보상 transaction
                    log.info("error message={}",commandMessage);
                    log.info("error message={}",commandResultMessage);
                    log.info("[보상Transaction] cancel order");

                    //db 결제 데이터 삭제
                    commandGateway.send(new CancelPaymentCommand(event.getOrderId(),event.getPaymentKey()));
                }
            }
        });
    }






}

