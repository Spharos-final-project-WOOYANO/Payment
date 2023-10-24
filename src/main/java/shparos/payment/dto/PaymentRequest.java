package shparos.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import shparos.payment.domain.PayStatus;
import shparos.payment.domain.PayType;

@Data
public class PaymentRequest {
    private String clientEmail; //사업자 이메일
    @Schema(description = "결제 키")
    private String paymentKey; //있
    @Schema(description = "주문 아이디")
    private String orderId; //있
    @Schema(description = "주문명")
    private String orderName; //있
    @Schema(description = "결제 수단")
    private PayType payType; //있
    @Schema(description = "총 결제 금액")
    private int totalAmount; //있
    @Schema(description = "결제가 일어난 날짜와 시간 정보")
    private LocalDate requestedAt; //있
    @Schema(description = "결제 승인이 일어난 날짜와 시간 정보")
    private LocalDate approvedAt; //있

    @Schema(description = "결제 처리 상태")
    private PayStatus payStatus;
}
