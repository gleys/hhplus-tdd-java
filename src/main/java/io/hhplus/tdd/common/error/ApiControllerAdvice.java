package io.hhplus.tdd.common.error;

import io.hhplus.tdd.point.error.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("400", e.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("500", e.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
