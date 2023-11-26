package spharos.payment.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spharos.payment.domain.PaymentStatus;
import spharos.payment.domain.PaymentStatusConverter;
import spharos.payment.domain.PaymentType;
import spharos.payment.domain.PaymentTypeConverter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchPayment {

    private String clientEmail; //사업자 이메일
    private int totalAmount; //결제 금액


}
