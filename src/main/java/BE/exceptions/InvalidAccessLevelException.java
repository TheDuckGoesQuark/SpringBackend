package BE.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidAccessLevelException extends BaseException{

    public InvalidAccessLevelException() {
        super(HttpStatus.BAD_REQUEST, "Invalid access level.", "Invalid access level.");
    }
}
