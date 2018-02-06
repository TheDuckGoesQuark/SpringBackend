package BE.exceptions;

import BE.security.AuthenticationFailureType;
import org.springframework.http.HttpStatus;

public class NotAuthorisedException extends BaseException {

    public NotAuthorisedException(AuthenticationFailureType reason) {
        super(HttpStatus.UNAUTHORIZED, reason.toString(), "Authorisation request failed.");
    }

}
