package rs.aleksa.membership.service;

import org.springframework.stereotype.Service;
import rs.aleksa.membership.dto.ApiDtos.*;
import rs.aleksa.membership.model.*;

@Service
public class MapperService {
    public MemberDto member(Member m) { return new MemberDto(m.getId(), m.getFirstName(), m.getLastName(), m.getPhone(), m.getEmail(), m.getNote(), m.getStatus().name(), m.getCreatedAt()); }
    public PlanDto plan(MembershipPlan p) { return new PlanDto(p.getId(), p.getName(), p.getCode(), p.getDurationDays(), p.getPrice(), p.isActive()); }
    public CardDto card(RfidCard c) { Member m = c.getMember(); return new CardDto(c.getId(), c.getCardId(), m.getId(), m.getFirstName() + " " + m.getLastName(), c.isActive(), c.getIssuedAt(), c.getDeactivatedAt()); }
    public SubscriptionDto subscription(Subscription s) { Member m=s.getMember(); MembershipPlan p=s.getPlan(); return new SubscriptionDto(s.getId(), m.getId(), m.getFirstName()+" "+m.getLastName(), p.getId(), p.getName(), s.getStartDate(), s.getEndDate(), s.getPrice(), s.getStatus().name(), s.getCreatedAt()); }
    public PaymentDto payment(Payment p) { return new PaymentDto(p.getId(), p.getMember().getId(), p.getSubscription()==null?null:p.getSubscription().getId(), p.getAmount(), p.getPaymentMethod(), p.getNote(), p.getPaymentDate()); }
    public AccessLogDto log(AccessLog a) { Member m=a.getMember(); return new AccessLogDto(a.getId(), a.getCardId(), m==null?null:m.getId(), m==null?null:m.getFirstName()+" "+m.getLastName(), a.isAllowed(), a.getReason(), a.getRequestTime()); }
}
