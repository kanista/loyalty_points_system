package com.example.loyalty_points_system.exception;

import com.example.loyalty_points_system.dto.CommonApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        CommonApiResponse<Void> response = new CommonApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientPointsException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleInsufficientPoints(InsufficientPointsException e) {
        CommonApiResponse<Void> response = new CommonApiResponse<>(HttpStatus.CONFLICT.value(), e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleCustomerNotFound(CustomerNotFoundException e) {
        CommonApiResponse<Void> response = new CommonApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPointsException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleInvalidPoints(InvalidPointsException e) {
        CommonApiResponse<Void> response = new CommonApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleGenericException(RuntimeException e) {
        CommonApiResponse<Void> response = new CommonApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred", null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static class InsufficientPointsException extends RuntimeException {
        public InsufficientPointsException(String message) {
            super(message);
        }
    }

    public static class CustomerNotFoundException extends RuntimeException {
        public CustomerNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidPointsException extends RuntimeException {
        public InvalidPointsException(String message) {
            super(message);
        }
    }

    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }


}

