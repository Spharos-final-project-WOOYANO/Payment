package spharos.payment.axon.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentCancelEvent {
    private String orderId;
    private String paymentKey;
}
