package BE.exceptions;

import org.springframework.http.HttpStatus;

public class ProjectNotFoundException extends BaseException {

    public ProjectNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Project not found.", "Project not found.");
    }
}
