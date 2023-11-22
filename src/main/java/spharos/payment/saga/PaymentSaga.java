/*
package spharos.payment.saga;


import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import spharos.payment.axon.event.PaymentSaveEvent;

@Saga
@Slf4j
public class PaymentSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "paymentId")
    public void createOrder(PaymentSaveEvent event) {
        log.info("[StartSaga] saga");

        commandGateway.send(new ChangeQuantityCommand(event.getProductId(), event.getQuantity()), new CommandCallback<ChangeQuantityCommand, Object>() {
            @Override
            public void onResult(CommandMessage<? extends ChangeQuantityCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {
                if(commandResultMessage.isExceptional()){
                    // 보상 transaction
                    log.info("[보상Transaction] cancel order");
                    commandGateway.send(new CancelOrderCommand(event.getOrderId()));
                }
            }
        });
    }
}
*/
