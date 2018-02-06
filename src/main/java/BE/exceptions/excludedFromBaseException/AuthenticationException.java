package BE.exceptions.excludedFromBaseException;

import BE.security.enums.AuthenticationFailureType;
import org.json.JSONObject;

public class AuthenticationException extends org.springframework.security.core.AuthenticationException {
    
    private AuthenticationFailureType authenticationFailureType;

    public AuthenticationException(String msg, AuthenticationFailureType authenticationFailureType) {
        super(msg);
        this.authenticationFailureType = authenticationFailureType;
    }

    public JSONObject getErrorResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", authenticationFailureType.toString());
        jsonObject.put("error_description", this.getMessage());
        return jsonObject;
    }
}
