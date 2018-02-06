package BE.security;

public enum GrantTypes {

    REFRESH_TOKEN("refresh_token"), PASSWORD("password");

    private String type;

    GrantTypes(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
