package BE.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException() {
        super(HttpStatus.ALREADY_REPORTED, "Username already in database.", "Username taken.");
    }
}
