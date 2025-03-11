package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(ResourceNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoOfSeatsLimitExceedException.class)
	public ResponseEntity<String> handleUserNotFoundException(NoOfSeatsLimitExceedException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SameFromAndToException.class)
    public ResponseEntity<Map<String, String>> handleSameFromAndToException(SameFromAndToException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignStatusException(FeignException ex) {
        String message;
        switch (ex.status()) {
            case 404:
                message = "The requested resource was not found";
                break;
            case 400:
                message = "There was a problem with your request. Please check the input parameters and try again.";
                break;
            case 500:
                message = "An internal server error occurred while processing your request. Please try again later.";
                break;
            default:
                message = "An unexpected error occurred: " + ex.getMessage();
                break;
        }
        return new ResponseEntity<>(message, HttpStatus.valueOf(ex.status()));
    }
 

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations()
				.forEach(violation -> errors.put(violation.getPropertyPath().toString(), violation.getMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

}
