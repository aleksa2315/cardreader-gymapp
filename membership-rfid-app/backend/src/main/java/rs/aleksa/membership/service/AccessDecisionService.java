package rs.aleksa.membership.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.aleksa.membership.dto.AccessDtos.AccessCheckResponse;
import rs.aleksa.membership.model.*;
import rs.aleksa.membership.repo.*;
import java.time.LocalDate;
import java.util.List;

@Service
public class AccessDecisionService {
    private final RfidCardRepository cards;
    private final SubscriptionRepository subscriptions;
    private final AccessLogRepository logs;
    public AccessDecisionService(RfidCardRepository cards, SubscriptionRepository subscriptions, AccessLogRepository logs) {
        this.cards = cards;
        this.subscriptions = subscriptions;
        this.logs = logs;
    }

    @Transactional
    public AccessCheckResponse check(String rawCardId) {
        String cardId = rawCardId == null ? "" : rawCardId.trim();
        boolean allowed = false;
        String reason;
        Member member = null;

        if (cardId.isBlank()) {
            reason = "EMPTY_CARD_ID";
        } else {
            var opt = cards.findByCardId(cardId);
            if (opt.isEmpty()) {
                reason = "CARD_NOT_FOUND";
            } else {
                RfidCard card = opt.get();
                member = card.getMember();
                if (!card.isActive()) {
                    reason = "CARD_INACTIVE";
                } else if (member.getStatus() != Enums.MemberStatus.ACTIVE) {
                    reason = "MEMBER_NOT_ACTIVE";
                } else {
                    List<Subscription> active = subscriptions.activeForMember(member.getId(), LocalDate.now(), Enums.SubscriptionStatus.ACTIVE);
                    if (active.isEmpty()) {
                        reason = "NO_ACTIVE_SUBSCRIPTION";
                    } else {
                        allowed = true;
                        reason = "ACCESS_GRANTED";
                    }
                }
            }
        }

        AccessLog log = new AccessLog();
        log.setCardId(cardId.isBlank() ? "" : cardId);
        log.setMember(member);
        log.setAllowed(allowed);
        log.setReason(reason);
        logs.save(log);

        return new AccessCheckResponse(cardId, allowed ? 1 : 0);
    }
}
