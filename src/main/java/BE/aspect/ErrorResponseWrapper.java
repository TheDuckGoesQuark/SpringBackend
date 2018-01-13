package BE.aspect;

import BE.exceptions.BaseException;

public class ErrorResponseWrapper {

    private String status;
    private String error;
    private String error_description;
    private String user_message;
    private Object error_data;

    ErrorResponseWrapper(BaseException exception) {
        this.status = exception.getStatus();
        this.error = Integer.toString(exception.getError());
        this.error_description = exception.getError_description();
        this.user_message = exception.getUser_message();
        this.error_data = exception.getError_data();
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getError_description() {
        return error_description;
    }

    public String getUser_message() {
        return user_message;
    }

    public Object getError_data() {
        return error_data;
    }
}
