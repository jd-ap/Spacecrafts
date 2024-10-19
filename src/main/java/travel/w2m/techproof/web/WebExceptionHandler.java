package travel.w2m.techproof.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    void handleGeneralException(Exception exception) {
        log.error("internal_error: {}", exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    void handleMethodArgumentNotValidException(MethodArgumentTypeMismatchException exception) {
        log.debug("parameter_error.{}: {}", exception.getName(), exception.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handleNoSuchElementException(NoSuchElementException exception) {
        log.debug("no_such_element_error: {}", exception.getLocalizedMessage());
    }
}
