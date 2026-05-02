package rs.aleksa.membership.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.aleksa.membership.model.RfidCard;

import java.util.List;
import java.util.Optional;

public interface RfidCardRepository extends JpaRepository<RfidCard, Long> {

    Optional<RfidCard> findByCardId(String cardId);

    boolean existsByCardId(String cardId);

    @Query("""
           select c
           from RfidCard c
           join fetch c.member m
           order by c.issuedAt desc
           """)
    List<RfidCard> findAllWithMember();

    @Query("""
           select c
           from RfidCard c
           join fetch c.member m
           where c.cardId = :cardId
           """)
    Optional<RfidCard> findByCardIdWithMember(@Param("cardId") String cardId);

    @Query("""
           select c
           from RfidCard c
           join fetch c.member m
           where m.id = :memberId
           order by c.id desc
           """)
    List<RfidCard> findByMemberIdWithMember(@Param("memberId") Long memberId);
}