package shparos.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clientEmail; //사업자 이메일
    @Column(nullable = false)
    private String paymentKey; //결제 키
    @Column(nullable = false)
    private String orderId; //주문 아이디
    @Column(nullable = false)
    private String orderName; //주문명
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PayType payType; //결제수단 결제수단입니다. 카드, 가상계좌, 간편결제, 휴대폰, 계좌이체

    @Column(nullable = false)
    private int totalAmount; //결제 금액

    @Column(nullable = false)
    private LocalDate requestedAt; //결제가 일어난 날짜와 시간 정보

    @Column(nullable = false)
    private LocalDate approvedAt; //결제 승인이 일어난 날짜와 시간 정보

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus; //결제 승인이 일어난 날짜와 시간 정보



    private Payment(String clientEmail, String paymentKey, String orderId,
                    String orderName, PayType payType, int totalAmount,
                    LocalDate requestedAt, LocalDate approvedAt,PayStatus payStatus) {
        this.clientEmail = clientEmail;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.orderName = orderName;
        this.payType = payType;
        this.totalAmount = totalAmount;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.payStatus = payStatus;
    }

    public static Payment createPayment(String clientEmail, String paymentKey, String orderId,
                                        String orderName, PayType payType, int totalAmount,
                                        LocalDate requestedAt, LocalDate approvedAt,PayStatus payStatus) {
        return new Payment(clientEmail, paymentKey, orderId, orderName, payType,
                totalAmount, requestedAt, approvedAt,payStatus);
    }


}
