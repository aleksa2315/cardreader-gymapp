package rs.aleksa.membership.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.aleksa.membership.model.Payment;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByMemberIdOrderByPaymentDateDesc(Long memberId);
}
