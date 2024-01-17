package spharos.payment.axon.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CancelPaymentCommand {

    @TargetAggregateIdentifier
    private String orderId;

    private String paymentKey;
}
