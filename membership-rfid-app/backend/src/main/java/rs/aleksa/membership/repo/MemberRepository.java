package rs.aleksa.membership.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.aleksa.membership.model.Member;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where :q is null or :q = '' or lower(concat(m.firstName,' ',m.lastName,' ',coalesce(m.phone,''),' ',coalesce(m.email,''))) like lower(concat('%', :q, '%')) order by m.lastName, m.firstName")
    List<Member> search(@Param("q") String q);
}
