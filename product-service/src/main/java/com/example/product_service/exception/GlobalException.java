package com.example.product_service.exception;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalException extends RuntimeException {
        private Map<String, Object> buildError(int status, String error, String message, String path) {
                return Map.of(
                                "status", status, "error", error, "message", message, "path", path, "timestamp",
                                Instant.now().toString());
        }

        @ExceptionHandler(ProductNotFoundException.class)
        public ResponseEntity<Map<String, Object>> handleNotFound(
                        ProductNotFoundException ex, HttpServletRequest req) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(buildError(404, "Not Found", ex.getMessage(), req.getRequestURI()));
        }

        @ExceptionHandler(InsufficientStockException.class)
        public ResponseEntity<Map<String, Object>> handleInsufficientStock(
                        InsufficientStockException ex, HttpServletRequest req) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                                .body(buildError(422, "Unprocessable Entity", ex.getMessage(), req.getRequestURI()));
        }

        // Validasi @Valid gagal → 400
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> handleValidation(
                        MethodArgumentNotValidException ex, HttpServletRequest req) {
                String message = ex.getBindingResult().getFieldErrors()
                                .stream()
                                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                                .findFirst().orElse("Validasi gagal");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(buildError(400, "Bad Request", message, req.getRequestURI()));
        }
}
