package BE.exceptions;

public abstract class BaseException extends Exception {
    private int error;
    private String error_description;
    private String user_message;
    private Object error_data;

    public BaseException(int error, String error_description, String user_message, Object error_data) {
        this.error = error;
        this.error_description = error_description;
        this.user_message = user_message;
        this.error_data = error_data;
    }

    public BaseException(int error, String error_description, String user_message) {
        this.setError(error);
        this.setError_description(error_description);
        this.setUser_message(user_message);
        this.setError_data(new Object());
    }

    public String getStatus() {
        return "error";
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
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

    public Object getError_data() {
        return error_data;
    }

    public void setError_data(Object error_data) {
        this.error_data = error_data;
    }
}
