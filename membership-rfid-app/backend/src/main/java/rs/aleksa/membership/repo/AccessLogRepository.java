package rs.aleksa.membership.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.aleksa.membership.model.AccessLog;
import java.time.LocalDateTime;
import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    List<AccessLog> findTop50ByOrderByRequestTimeDesc();
    long countByRequestTimeAfter(LocalDateTime after);
    @Query("select a from AccessLog a order by a.requestTime desc")
    List<AccessLog> latest();
}
