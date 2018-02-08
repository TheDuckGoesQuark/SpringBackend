package BE.exceptions;

import org.springframework.http.HttpStatus;

public class UnsupportedFileViewException extends BaseException {
    public UnsupportedFileViewException() {
        super(HttpStatus.NOT_FOUND, "View not found", "unsupported_file_view");
    }
}
