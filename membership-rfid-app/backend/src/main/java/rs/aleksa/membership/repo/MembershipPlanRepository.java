package rs.aleksa.membership.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.aleksa.membership.model.MembershipPlan;
import java.util.List;
import java.util.Optional;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {
    Optional<MembershipPlan> findByCode(String code);
    List<MembershipPlan> findByActiveTrueOrderByIdAsc();
}
