package BE.exceptions;

import org.springframework.http.HttpStatus;

public class LogValueAlreadyExistsException extends BaseException {

    public LogValueAlreadyExistsException() {
        super(HttpStatus.ALREADY_REPORTED,"Log value already in database.","Log value taken.");
    }
}
