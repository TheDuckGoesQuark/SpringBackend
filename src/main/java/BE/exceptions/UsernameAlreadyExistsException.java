package BE.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends BaseException {

    public UsernameAlreadyExistsException() {
        super(HttpStatus.ALREADY_REPORTED, "Username already in database.", "Username taken.");
    }
}
