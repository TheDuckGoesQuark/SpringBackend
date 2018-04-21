package BE.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidMetadataException extends BaseException {
    public InvalidMetadataException() {
        super(HttpStatus.BAD_REQUEST, "invalid_metadata_version", "Unable to process your request. Please try again.");
    }
}
