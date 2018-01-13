package BE.exceptions;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super(404, "User not found.", "User not found.");
    }

    public UserNotFoundException(Object error_data) {
        super(404, "User not found.", "User not found.", error_data);
    }
}
