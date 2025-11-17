package com.sulwork.cafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiError> handleBusiness(BusinessException ex, HttpServletRequest req) {
    var status = HttpStatus.valueOf(ex.getStatus());
    var body = new ApiError(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
        List.of(ex.getMessage()), req.getRequestURI());
    return ResponseEntity.status(status).body(body);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    var msgs = ex.getBindingResult().getFieldErrors().stream()
        .map(err -> err.getField() + ": " + err.getDefaultMessage()).toList();
    var body = new ApiError(LocalDateTime.now(), 422, "Unprocessable Entity", msgs, req.getRequestURI());
    return ResponseEntity.unprocessableEntity().body(body);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
    var body = new ApiError(LocalDateTime.now(), 500, "Internal Server Error",
        List.of(ex.getMessage()), req.getRequestURI());
    return ResponseEntity.status(500).body(body);
  }
}
