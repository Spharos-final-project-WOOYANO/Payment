package shparos.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import shparos.payment.domain.PaymentType;

@Data
public class PaymentResultResponseList {

    private String clientEmail; //사업자 이메일

    private int totalAmount; //있

    private LocalDateTime approvedAt; //있

}
