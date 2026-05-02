package rs.aleksa.membership.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_logs")
public class AccessLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "card_id", nullable = false)
    private String cardId;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;
    @Column(nullable = false)
    private boolean allowed;
    @Column(nullable = false, length = 100)
    private String reason;
    @Column(name = "request_time", insertable = false, updatable = false)
    private LocalDateTime requestTime;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCardId() { return cardId; }
    public void setCardId(String cardId) { this.cardId = cardId; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public boolean isAllowed() { return allowed; }
    public void setAllowed(boolean allowed) { this.allowed = allowed; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public LocalDateTime getRequestTime() { return requestTime; }
}
