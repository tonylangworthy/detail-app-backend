package com.webbdealer.detailing.shared;

import com.webbdealer.detailing.job.InvalidJobActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomResponseExceptionHandler {

    private static  final Logger logger = LoggerFactory.getLogger(CustomResponseExceptionHandler.class);

    @ExceptionHandler(InvalidJobActionException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleInvalidJobActionException(InvalidJobActionException ex) {
        return new ApiErrorResponse(HttpStatus.BAD_REQUEST, 400, ex.getLocalizedMessage(), ex.toString());
    }
}
