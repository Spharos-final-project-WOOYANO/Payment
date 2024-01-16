package spharos.payment.saga;


import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import spharos.payment.axon.command.CancelPaymentCommand;
import spharos.payment.axon.command.SavePaymentCommand;
import spharos.payment.axon.event.PaymentSaveEvent;
import spharos.reservations.axon.command.CreateReservationCommand;

@Slf4j
@Saga
public class ReserSaga {

    @Autowired
    private transient CommandGateway commandGateway;


    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(PaymentSaveEvent event) {

        log.info("[StartSaga] saga");
        // on동작에서 associationProperty 설정한거에서 핸들작업 쭉 과정에서 하나로 이어주는게 associationProperty
        //앞으로의 핸들 동작을 위해서 어떤것을 associationProperty 정의
       // SagaLifecycle.associateWith("reservation_num", event.getReservation_num());


        commandGateway.send(new CreateReservationCommand(event.getOrderId(),event.getAmount(),event.getServiceId(),
                event.getUserEmail(),event.getReservationDate(),event.getRequest(),event.getAddress(),event.getServiceStart()
        ,event.getWorkerId()), new CommandCallback<CreateReservationCommand, Object>() {
            @Override
            public void onResult(CommandMessage<? extends CreateReservationCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {
                if(commandResultMessage.isExceptional()){
                    // 보상 transaction
                    log.info("[보상Transaction] cancel order");
                    //결제 취소

                    //db 결제 데이터 삭제
                    commandGateway.send(new CancelPaymentCommand(event.getOrderId(),event.getPaymentKey()));
                }
            }
        });
    }






}

