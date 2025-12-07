package com.doctorservice.exception;
import com.doctorservice.dto.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle Custom Exceptions
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<APIResponse<?>> handleCustomException(CustomException ex) {

        APIResponse<Object> response = new APIResponse<>();
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle All Other Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<?>> handleGlobalException(Exception ex) {

        APIResponse<Object> response = new APIResponse<>();
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}