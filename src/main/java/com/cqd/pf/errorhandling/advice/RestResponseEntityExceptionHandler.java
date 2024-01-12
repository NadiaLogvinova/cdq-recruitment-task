package com.cqd.pf.errorhandling.advice;

import com.cqd.pf.errorhandling.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException ex) {
        log.debug("Service exception", ex);
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }
}
