package BE.exceptions;

import org.springframework.http.HttpStatus;

public class NotAuthorisedException extends BaseException {

    public NotAuthorisedException(AuthorisationFailureTypes reason) {
        super(HttpStatus.UNAUTHORIZED, reason.toString(), "Authorisation request failed.");
    }

}
