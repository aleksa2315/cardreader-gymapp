package rs.aleksa.membership.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.aleksa.membership.model.Subscription;
import rs.aleksa.membership.model.Enums;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // OVO koristimo za prikaz članarina po članu - odmah učitava member i plan
    @Query("""
           select s
           from Subscription s
           join fetch s.member m
           join fetch s.plan p
           where m.id = :memberId
           order by s.endDate desc, s.id desc
           """)
    List<Subscription> findByMemberIdWithDetails(@Param("memberId") Long memberId);

    // Možeš ostaviti staru metodu ako se negde još koristi, ali za UI koristi novu gore
    List<Subscription> findByMemberIdOrderByEndDateDesc(Long memberId);

    @Query("""
           select s
           from Subscription s
           where s.member.id = :memberId
             and s.status = :status
             and s.startDate <= :today
             and s.endDate >= :today
           order by s.endDate desc
           """)
    List<Subscription> activeForMember(
            @Param("memberId") Long memberId,
            @Param("today") LocalDate today,
            @Param("status") Enums.SubscriptionStatus status
    );

    @Query("""
           select count(s)
           from Subscription s
           where s.status = :status
             and s.startDate <= :today
             and s.endDate >= :today
           """)
    long countActive(
            @Param("today") LocalDate today,
            @Param("status") Enums.SubscriptionStatus status
    );
}