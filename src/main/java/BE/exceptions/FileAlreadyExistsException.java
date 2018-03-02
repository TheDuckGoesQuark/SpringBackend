package BE.exceptions;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.http.HttpStatus;

public class FileAlreadyExistsException extends BaseException {
    public FileAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "File already exists.", "file_already_exists");
    }
}
