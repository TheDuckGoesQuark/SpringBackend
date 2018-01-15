package BE.advices;

import BE.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class ErrorResponseWrapper {

    private String status;
    private HttpStatus error;
    private String error_description;
    private String user_message;

    ErrorResponseWrapper(BaseException exception) {
        this.status = exception.getStatus();
        this.error = exception.getError();
        this.error_description = exception.getError_description();
        this.user_message = exception.getUser_message();
    }

    public String getStatus() {
        return status;
    }

    public HttpStatus getError() {
        return error;
    }

    public String getError_description() {
        return error_description;
    }

    public String getUser_message() {
        return user_message;
    }
}
