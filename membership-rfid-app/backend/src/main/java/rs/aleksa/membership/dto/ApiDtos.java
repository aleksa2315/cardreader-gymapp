package rs.aleksa.membership.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class ApiDtos {
    private ApiDtos() {}

    public record MemberDto(Long id, String firstName, String lastName, String phone, String email, String note, String status, LocalDateTime createdAt) {}
    public record MemberRequest(String firstName, String lastName, String phone, String email, String note, String status) {}

    public record PlanDto(Long id, String name, String code, Integer durationDays, BigDecimal price, boolean active) {}
    public record PlanRequest(String name, String code, Integer durationDays, BigDecimal price, Boolean active) {}

    public record CardDto(Long id, String cardId, Long memberId, String memberName, boolean active, LocalDateTime issuedAt, LocalDateTime deactivatedAt) {}
    public record CardRequest(String cardId, Long memberId) {}

    public record SubscriptionDto(Long id, Long memberId, String memberName, Long planId, String planName, LocalDate startDate, LocalDate endDate, BigDecimal price, String status, LocalDateTime createdAt) {}
    public record SubscriptionRequest(Long memberId, Long planId, LocalDate startDate, BigDecimal price) {}

    public record PaymentDto(Long id, Long memberId, Long subscriptionId, BigDecimal amount, String paymentMethod, String note, LocalDateTime paymentDate) {}

    public record AccessLogDto(Long id, String cardId, Long memberId, String memberName, boolean allowed, String reason, LocalDateTime requestTime) {}
    public record DashboardDto(long activeMembers, long activeSubscriptions, long todayAccessAttempts) {}
}
