package spharos.payment.application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import spharos.payment.dto.PaymentRequest;
import spharos.payment.dto.PaymentResultResponse;

public interface PaymentService {

    void apporvePayment(String paymentKey, String orderId, int amount,
                                                 Long serviceId, Long workerId, String userEmail,
                                                 LocalDate reservationDate, String request, String address,
                                                 String clientEmail, LocalTime serviceStart, List<Long> reservationGoodsId);
     void savePayment(PaymentRequest paymentRequest);
    List<PaymentResultResponse> getPaymentsList();

}
