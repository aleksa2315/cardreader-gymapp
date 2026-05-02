package rs.aleksa.membership.dto;

public final class AccessDtos {
    private AccessDtos() {}
    public record AccessCheckRequest(String cardId) {}
    public record AccessCheckResponse(String cardId, int allowed) {}
}
