package BE.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidPropertyException extends BaseException{

    public InvalidPropertyException() {
        super(HttpStatus.BAD_REQUEST, "Invalid property.", "Invalid property.");
    }
}
