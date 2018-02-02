package BE.exceptions;

import org.springframework.http.HttpStatus;

public class FileNotFoundException extends BaseException {

    public FileNotFoundException() {
        super(HttpStatus.NOT_FOUND, "File not found.", "invalid_path");
    }
}
