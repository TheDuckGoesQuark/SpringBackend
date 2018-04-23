package BE.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidLoggingLevelException extends BaseException {

    public InvalidLoggingLevelException() {
        super(HttpStatus.BAD_REQUEST, "Invalid level.", "Invalid level.");
    }
}
