package BE.advices;

import BE.exceptions.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class JSONErrorAdvisor {

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponseWrapper> handleResourceNotFound(BaseException ex){
        return ResponseEntity
                .status(ex.getError())
                .body(new ErrorResponseWrapper(ex));
    }
}