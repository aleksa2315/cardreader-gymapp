package rs.aleksa.membership.model;

public final class Enums {
    private Enums() {}
    public enum UserRole { ADMIN, STAFF }
    public enum MemberStatus { ACTIVE, BLOCKED, INACTIVE }
    public enum SubscriptionStatus { ACTIVE, EXPIRED, CANCELLED }
}
