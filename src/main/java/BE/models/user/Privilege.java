package BE.models.user;

public enum Privilege {
    ADMIN, USER;

    public String toString() {
        return this.name().toLowerCase();
    }
}
