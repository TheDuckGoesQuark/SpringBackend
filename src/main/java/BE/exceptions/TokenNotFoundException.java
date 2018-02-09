package BE.exceptions;

import org.springframework.http.HttpStatus;

public class TokenNotFoundException extends BaseException {
    public TokenNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Token id doesn't exist in store.", "Authorisation error.");
    }
}
