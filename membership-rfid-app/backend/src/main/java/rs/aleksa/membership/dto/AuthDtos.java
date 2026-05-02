package rs.aleksa.membership.dto;

public final class AuthDtos {
    private AuthDtos() {}
    public record LoginRequest(String username, String password) {}
    public record LoginResponse(String token, String username, String fullName, String role) {}
}
