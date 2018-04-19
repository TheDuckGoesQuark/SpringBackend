package BE.exceptions;

import org.springframework.http.HttpStatus;

public class GenericInternalServerException extends BaseException {
    public GenericInternalServerException(Exception e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Internal failure or uncaught exception: "+e.getMessage(), "Something went wrong when processing your request.");
    }
}
