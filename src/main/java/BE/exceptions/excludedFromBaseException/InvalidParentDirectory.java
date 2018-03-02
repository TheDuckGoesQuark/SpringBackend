package BE.exceptions.excludedFromBaseException;


import BE.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidParentDirectory extends BaseException {

    public InvalidParentDirectory() {
        super(HttpStatus.NOT_FOUND, "Parent directory not found.", "invalid_parent_directory");
    }
}
