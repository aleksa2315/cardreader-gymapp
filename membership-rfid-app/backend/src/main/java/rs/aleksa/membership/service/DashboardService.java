package rs.aleksa.membership.service;

import org.springframework.stereotype.Service;
import rs.aleksa.membership.dto.ApiDtos.DashboardDto;
import rs.aleksa.membership.model.Enums;
import rs.aleksa.membership.repo.AccessLogRepository;
import rs.aleksa.membership.repo.MemberRepository;
import rs.aleksa.membership.repo.SubscriptionRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DashboardService {
    private final MemberRepository members; private final SubscriptionRepository subscriptions; private final AccessLogRepository logs;
    public DashboardService(MemberRepository members, SubscriptionRepository subscriptions, AccessLogRepository logs){this.members=members;this.subscriptions=subscriptions;this.logs=logs;}
    public DashboardDto stats(){ return new DashboardDto(members.count(), subscriptions.countActive(LocalDate.now(), Enums.SubscriptionStatus.ACTIVE), logs.countByRequestTimeAfter(LocalDateTime.now().toLocalDate().atStartOfDay())); }
}
