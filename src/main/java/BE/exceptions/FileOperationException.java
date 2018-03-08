package BE.exceptions;

import org.springframework.http.HttpStatus;

public class FileOperationException extends BaseException {
    public FileOperationException(Exception e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "Failed when trying to perform file operation.");
    }
}
