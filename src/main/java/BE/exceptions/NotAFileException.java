package BE.exceptions;

import org.springframework.http.HttpStatus;

public class NotAFileException extends BaseException {
    public NotAFileException() {
        super(HttpStatus.BAD_REQUEST, "not_a_file", "You cannot overwrite a directory.");
    }
}
