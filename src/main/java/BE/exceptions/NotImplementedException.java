package BE.exceptions;

import org.springframework.http.HttpStatus;

public class NotImplementedException extends BaseException {

    public NotImplementedException() {
        super(HttpStatus.NOT_IMPLEMENTED, "Protocol not yet implemented.", "That service does not exist yet.");
    }
}
