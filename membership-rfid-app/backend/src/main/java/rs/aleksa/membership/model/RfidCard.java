package rs.aleksa.membership.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rfid_cards")
public class RfidCard {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "card_id", nullable = false, unique = true, length = 100)
    private String cardId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @Column(nullable = false)
    private boolean active = true;
    @Column(name = "issued_at", insertable = false, updatable = false)
    private LocalDateTime issuedAt;
    @Column(name = "deactivated_at")
    private LocalDateTime deactivatedAt;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCardId() { return cardId; }
    public void setCardId(String cardId) { this.cardId = cardId; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDateTime getIssuedAt() { return issuedAt; }
    public LocalDateTime getDeactivatedAt() { return deactivatedAt; }
    public void setDeactivatedAt(LocalDateTime deactivatedAt) { this.deactivatedAt = deactivatedAt; }
}
