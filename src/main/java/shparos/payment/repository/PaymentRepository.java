package shparos.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shparos.payment.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
