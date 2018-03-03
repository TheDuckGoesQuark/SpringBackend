package BE.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidRequestStructureException extends BaseException {
    public InvalidRequestStructureException() {
        super(HttpStatus.BAD_REQUEST, "Structure of request does not match required models.", "The details provided are incorrectly formed. ");
    }
    public InvalidRequestStructureException(String extraInformation) {
        super(HttpStatus.BAD_REQUEST, "Structure of request does not match required models. "+extraInformation, "The details provided are incorrectly formed. ");
    }
}
