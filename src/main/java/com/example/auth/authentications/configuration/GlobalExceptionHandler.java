package com.example.auth.authentications.configuration;

import com.example.auth.authentications.domain.exception.ApiCallErrorObject;
import com.example.auth.authentications.domain.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.nio.file.AccessDeniedException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(HttpServletRequest request, NotFoundException ex){
        logger.error("NotFoundException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiCallErrorObject<String>("Not found exception", List.of(ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalServerError(HttpServletRequest request, Exception ex){
        logger.error("handleInternalServerError {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiCallErrorObject<String>());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException ex){
        logger.error("handleMethodArgumentTypeMismatchException {}\n", request.getRequestURI(), ex);

        Map<String, String> details = new HashMap<>();
        details.put("paramName", ex.getName());
        details.put("paramValue", Optional.of(ex.getName())
                .map(Objects::toString).orElse(""));
        details.put("errorMessage", ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ApiCallErrorObject<Map<String, String>>("Method argument type mismatch", List.of(details)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex){
        logger.error("handleMethodArgumentNotValidException {}\n", request.getRequestURI(), ex);

        List<Map<String, String>> details = getExceptionDetails(ex);
        return ResponseEntity.badRequest()
                .body(new ApiCallErrorObject<String>("Method argument validation failed", details));
    }

//    @org.jetbrains.annotations.NotNull
    private List<Map<String, String>> getExceptionDetails(MethodArgumentNotValidException ex) {
        List<Map<String, String>> details = new ArrayList<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach( fieldError -> {
                    Map<String, String> detail = new HashMap<>();
                    detail.put("objectName", fieldError.getObjectName());
                    detail.put("field", fieldError.getField());
                    detail.put("rejectedValue", String.valueOf(fieldError.getRejectedValue()));
                    detail.put("errorMessage", fieldError.getDefaultMessage());
                    details.add(detail);
                });
        return details;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex){
        logger.error("handleAccessDeniedException {}\n", request.getRequestURI(),ex);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiCallErrorObject<String>("Access denied", List.of(ex.getMessage())));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(HttpServletRequest request, ValidationException ex){
        logger.error("ValidationException {}\n", request.getRequestURI(), ex);

        return ResponseEntity.badRequest()
                .body(new ApiCallErrorObject<String>("Validation Exception", List.of(ex.getMessage())));
    }
}
