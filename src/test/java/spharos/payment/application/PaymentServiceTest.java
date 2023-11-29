package spharos.payment.application;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spharos.payment.domain.Payment;
import spharos.payment.domain.enumPackage.PaymentStatus;
import spharos.payment.domain.enumPackage.PaymentType;
import spharos.payment.infrastructure.PaymentRepository;


@SpringBootTest
class PaymentServiceTest {


    @Autowired
    private PaymentRepository paymentRepository; // Payment 엔터티를 다루는 레포지토리 인터페이스

    @Test
    public void 결제완료더미데이터저장() {
        // Given

        String clientEmail = "test";
        PaymentType paymentType = PaymentType.CARD;
        int totalAmount = 100000;

        LocalDateTime approvedAt = LocalDateTime.now();
        PaymentStatus paymentStatus = PaymentStatus.CANCEL;

        // When
        Payment payment = Payment.createPayment(clientEmail, paymentType, totalAmount, approvedAt, paymentStatus);
        Payment save = paymentRepository.save(payment);

        // Then
        assertEquals(clientEmail, save.getClientEmail());
    }



}

