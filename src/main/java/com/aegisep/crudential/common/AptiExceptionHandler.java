package com.aegisep.crudential.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AptiExceptionHandler {

    Logger logger = LoggerFactory.getLogger(AptiExceptionHandler.class);

    @ExceptionHandler(AptiException.class)
    public ResponseEntity<?> aegisepException(AptiException aptiException) {
        logger.error("Exception ==>" + aptiException.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(aptiException, httpStatus);
    }


}
