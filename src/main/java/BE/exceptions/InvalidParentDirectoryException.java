package BE.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidParentDirectoryException extends BaseException {

    public InvalidParentDirectoryException() {
        super(HttpStatus.NOT_FOUND, "Parent directory not found.", "invalid_parent_directory");
    }
}
