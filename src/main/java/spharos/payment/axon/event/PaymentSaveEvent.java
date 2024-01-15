package spharos.payment.axon.event;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentSaveEvent {

    private String orderId;

    private int amount;
    private String clientEmail;
    private String paymentKey;
    private int suppliedAmount;
    private int vat;
    private String status;
    private String method;
    private String approvedAt;
}
