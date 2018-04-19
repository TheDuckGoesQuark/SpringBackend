package BE.exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends BaseException {
    public CustomException(HttpStatus error, String error_description, String user_message) {
        super(error, error_description, user_message);
    }
}
