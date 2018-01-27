package BE.exceptions;

import org.springframework.http.HttpStatus;

public class ProjectAlreadyExistsException extends BaseException {

    public ProjectAlreadyExistsException() {
        super(HttpStatus.ALREADY_REPORTED, "Project name already in database.", "Project name taken.");
    }
}
