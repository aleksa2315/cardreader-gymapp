package rs.aleksa.membership.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.aleksa.membership.dto.ApiDtos.*;
import rs.aleksa.membership.model.*;
import rs.aleksa.membership.repo.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptions;
    private final MemberRepository members;
    private final MembershipPlanRepository plans;
    private final PaymentRepository payments;
    private final MapperService mapper;

    public SubscriptionService(SubscriptionRepository subscriptions,
                               MemberRepository members,
                               MembershipPlanRepository plans,
                               PaymentRepository payments,
                               MapperService mapper) {
        this.subscriptions = subscriptions;
        this.members = members;
        this.plans = plans;
        this.payments = payments;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<SubscriptionDto> byMember(Long memberId) {
        return subscriptions.findByMemberIdWithDetails(memberId)
                .stream()
                .map(mapper::subscription)
                .toList();
    }

    @Transactional
    public SubscriptionDto create(SubscriptionRequest r) {
        Member m = members.findById(r.memberId()).orElseThrow();
        MembershipPlan p = plans.findById(r.planId()).orElseThrow();

        LocalDate start = r.startDate() == null ? LocalDate.now() : r.startDate();

        Subscription s = new Subscription();
        s.setMember(m);
        s.setPlan(p);
        s.setStartDate(start);
        s.setEndDate(start.plusDays(p.getDurationDays() - 1));
        s.setPrice(r.price() == null ? p.getPrice() : r.price());
        s.setStatus(Enums.SubscriptionStatus.ACTIVE);

        s = subscriptions.save(s);

        Payment pay = new Payment();
        pay.setMember(m);
        pay.setSubscription(s);
        pay.setAmount(s.getPrice() == null ? BigDecimal.ZERO : s.getPrice());
        pay.setPaymentMethod("CASH");
        pay.setNote("Automatski kreirana uplata uz članarinu");

        payments.save(pay);

        return mapper.subscription(s);
    }
}