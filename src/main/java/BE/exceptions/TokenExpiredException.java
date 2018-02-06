package BE.exceptions;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends BaseException {
    public TokenExpiredException() {
        super(HttpStatus.GONE, "Token has expired.", "Your session has expired.");
    }
}
