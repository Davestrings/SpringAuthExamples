package com.example.auth.authentications.configuration;

import com.example.auth.authentications.domain.exception.ApiCallErrorObject;
import com.example.auth.authentications.domain.exception.InternalServerError;
import com.example.auth.authentications.domain.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionhandler {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(HttpServletRequest request, NotFoundException ex){
        logger.error("NotFoundException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiCallErrorObject<String>("Not found exception", List.of(ex.getMessage())));
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<?> handleInternalServerError(HttpServletRequest request, Exception ex){
        logger.error("handleInternalServerError {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiCallErrorObject<String>());
    }
}
