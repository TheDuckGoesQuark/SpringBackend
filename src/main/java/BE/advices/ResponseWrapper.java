package BE.advices;

public class ResponseWrapper {

    private final String status = "success";
    private Object data;

    ResponseWrapper(Object response) {
        this.data = response;
    }

    public String getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
