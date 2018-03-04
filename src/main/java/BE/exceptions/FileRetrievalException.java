package BE.exceptions;

import org.springframework.http.HttpStatus;

public class FileRetrievalException extends BaseException {
    public FileRetrievalException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Failed when retrieving file from storage.", "Failed to retrieve file.");
    }
}
