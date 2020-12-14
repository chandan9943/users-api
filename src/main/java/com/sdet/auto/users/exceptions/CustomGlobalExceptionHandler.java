package com.sdet.auto.users.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// adding @ControllerAdvice makes it applicable to all controller
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        // get all erros
        List<ObjectError> exceptions = ex.getBindingResult().getAllErrors();
        // get all exception messages
        List<String> exceptionMessages = new ArrayList<>();

        for (ObjectError exception : exceptions) {
                exceptionMessages.add(exception.getDefaultMessage());
        }
        // map exception messages to a string for errors field
        ObjectMapper objectMapper = new ObjectMapper();

        String errors = "";

        try {
            errors = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exceptionMessages);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),
                "from handleMethodArgumentNotValid method", errors);
        // return custom message in a response entity
        return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),
                "from handleHttpRequestMethodNotSupported in method", ex.getMessage());

        return new ResponseEntity<>(customErrorDetails, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
                                                                           WebRequest request) {

        CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
    }
}
