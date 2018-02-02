package BE.responsemodels.security;

public class TokenRequestModel {

    private String grant_type;
    private String refresh_token;
    private String username;
    private String password;

    protected TokenRequestModel() {
    }

    public TokenRequestModel(String grant_type, String refresh_token, String username, String password) {
        this.grant_type = grant_type;
        this.refresh_token = refresh_token;
        this.username = username;
        this.password = password;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
