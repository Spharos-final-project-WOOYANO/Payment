package spharos.payment.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.payment.domain.Payment;
import spharos.payment.dto.PaymentResultResponse;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


    @Query("SELECT new spharos.payment.dto.PaymentResultResponse(p.clientEmail,sum(p.totalAmount)) FROM Payment p WHERE p.approvedAt BETWEEN :startDate AND :endDate group by p.clientEmail")
    List<PaymentResultResponse> findByApprovedAtAndPaymentStatus(@Param("startDate") LocalDateTime startDate,
                                                                     @Param("endDate") LocalDateTime endDate);
}



