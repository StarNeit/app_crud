package com.test.delivery.config;

import com.test.delivery.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        log.error("Fail to process " + request.getContextPath(), ex);
        var rs = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleAllException(RuntimeException ex, WebRequest request) {
        log.error("Fail to process " + request.getContextPath(), ex);
        var rs = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotException(NotFoundException ex, WebRequest request) {
        var rs = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);
    }
}
