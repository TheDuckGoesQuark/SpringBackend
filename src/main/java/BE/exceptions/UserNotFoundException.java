package BE.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User not found.", "User not found.");
    }
}
