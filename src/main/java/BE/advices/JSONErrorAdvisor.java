package BE.advices;

import BE.exceptions.BaseException;
import BE.exceptions.GenericInternalServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class JSONErrorAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseWrapper> handleKnownExceptions(BaseException ex){
        return ResponseEntity
                .status(ex.getError())
                .body(new ErrorResponseWrapper(ex));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseWrapper> handleGenericException(Exception ex){
        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body(new ErrorResponseWrapper(new GenericInternalServerException(ex)));
    }
}