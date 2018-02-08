package BE.security.enums;

public enum AuthenticationFailureType {
    INVALID_GRANT("invalid_grant"), INVALID_REQUEST("invalid_request"), UNSUPPORTED_GRANT_TYPE("unsupported_grant_type");

    private String type;
    AuthenticationFailureType(String type) {
        this.type = type;
    }
}