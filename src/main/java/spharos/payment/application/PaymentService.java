package spharos.payment.application;

import java.util.List;
import spharos.payment.dto.PaymentRequest;
import spharos.payment.dto.PaymentResultResponse;

public interface PaymentService {
     void savePayment(PaymentRequest paymentRequest);
    List<PaymentResultResponse> getPaymentsList();

}
