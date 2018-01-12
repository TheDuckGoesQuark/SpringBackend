package BE.aspect;

public class ResponseWrapper {

    private final String status = "success";
    private Object data;

    public ResponseWrapper(Object response) {
        this.data = response;
    }
}
