package BE.aspect;

public class ErrorResponseWrapper {

    private final String status = "error";
    private String error;
    private String error_description;
    private Object error_data;

    public ErrorResponseWrapper(String error, String error_description, Object error_data) {
        this.error = error;
        this.error_description = error_description;
        this.error_data = error_data;
    }
}
