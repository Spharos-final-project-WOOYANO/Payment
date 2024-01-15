package spharos.payment.axon.command;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SavePaymentCommand {

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
