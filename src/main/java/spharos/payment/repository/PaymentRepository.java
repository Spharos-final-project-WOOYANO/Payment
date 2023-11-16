package spharos.payment.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.payment.domain.Payment;
import spharos.payment.domain.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    //@Query("select p from Payment p where p.paymentStatus = :paymentStatus")
    @Query("select p from Payment p where p.paymentStatus = :paymentStatus1 or p.paymentStatus = :paymentStatus2")
    List<Payment> findByPaymentStatus(@Param("paymentStatus1") PaymentStatus paymentStatus1, @Param("paymentStatus2") PaymentStatus paymentStatus2);


}
