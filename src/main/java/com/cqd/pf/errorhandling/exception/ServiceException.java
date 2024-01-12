package com.cqd.pf.errorhandling.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

@Getter
public class ServiceException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ServiceException(String message, HttpStatus httpStatus) {
        super(message);
        Assert.notNull(httpStatus, "'httpStatus' must not be null");
        Assert.hasText(message, "'message' must not be empty");
        this.httpStatus = httpStatus;
    }
}
