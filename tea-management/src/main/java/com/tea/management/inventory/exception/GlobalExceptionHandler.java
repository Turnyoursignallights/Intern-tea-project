package com.tea.management.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Handle tea not found
    @ExceptionHandler({TeaNotFoundException.class})
    public ResponseEntity<ProblemDetail> handleTeaNotFoundException(TeaNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setProperty("timestamp", System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }


    // Handle 404 errors (route not found)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoHandlerFoundException(NoHandlerFoundException ex) {

        // Step 1: Create a ProblemDetail instance with status and message
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Route Not Found");
        problemDetail.setDetail("The requested URL " + ex.getRequestURL() + " was not found on the server.");
        problemDetail.setProperty("timestamp", System.currentTimeMillis());
        problemDetail.setProperty("error", "No handler found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }
}
