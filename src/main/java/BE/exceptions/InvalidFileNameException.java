package BE.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidFileNameException extends BaseException {
    public InvalidFileNameException() {
        super(HttpStatus.BAD_REQUEST, "Names must contain 1 or more characters", "Names must contain 1 or more characters");
    }
}
