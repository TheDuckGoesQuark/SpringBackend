package BE.exceptions;


import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
    private HttpStatus error;
    private String error_description;
    private String user_message;

    public BaseException(HttpStatus error, String error_description, String user_message) {
        this.error = error;
        this.error_description = error_description;
        this.user_message = user_message;
    }

    public String getStatus() {
        return "error";
    }

    public HttpStatus getError() {
        return error;
    }

    public void setError(HttpStatus error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getUser_message() {
        return user_message;
    }

    public void setUser_message(String user_message) {
        this.user_message = user_message;
    }
}
