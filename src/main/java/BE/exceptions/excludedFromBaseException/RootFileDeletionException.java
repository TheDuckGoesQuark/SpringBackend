package BE.exceptions.excludedFromBaseException;

import BE.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class RootFileDeletionException extends BaseException {
    public RootFileDeletionException() {
        super(HttpStatus.METHOD_NOT_ALLOWED, "Cannot delete project root.", "Cannot delete project root. Did you mean to delete the project?");
    }
}
