package BE.exceptions;

import org.springframework.http.HttpStatus;

public class FileNotFoundException extends BaseException {

    public FileNotFoundException() {
        super(HttpStatus.NOT_FOUND, "MetaFile not found.", "file_not_found");
    }
}
