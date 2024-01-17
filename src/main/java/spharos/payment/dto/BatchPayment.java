package spharos.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchPayment {

    private String clientEmail; //사업자 이메일
    private int totalAmount; //결제 금액


}
