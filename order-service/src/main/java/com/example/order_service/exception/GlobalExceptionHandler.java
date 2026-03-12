// order-service/exception/GlobalExceptionHandler.java

package com.example.order_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;
import java.util.Map;

import javax.naming.ServiceUnavailableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─── Helper builder ───────────────────────────────────────────
    private Map<String, Object> buildError(int status, String error,
            String message, String path) {
        return Map.of(
                "status", status,
                "error", error,
                "message", message,
                "path", path,
                "timestamp", Instant.now().toString());
    }

    // ─── 404 Not Found ────────────────────────────────────────────
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOrderNotFound(
            OrderNotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(404, "Not Found", ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFound(
            ProductNotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(404, "Not Found", ex.getMessage(), req.getRequestURI()));
    }

    // ─── 409 Conflict ─────────────────────────────────────────────
    @ExceptionHandler(OrderCannotBeCancelledException.class)
    public ResponseEntity<Map<String, Object>> handleCannotCancel(
            OrderCannotBeCancelledException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildError(409, "Conflict", ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(OrderCannotBeConfirmedException.class)
    public ResponseEntity<Map<String, Object>> handleCannotConfirm(
            OrderCannotBeConfirmedException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildError(409, "Conflict", ex.getMessage(), req.getRequestURI()));
    }

    // ─── 422 Unprocessable Entity ─────────────────────────────────
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientStock(
            InsufficientStockException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(buildError(422, "Unprocessable Entity", ex.getMessage(), req.getRequestURI()));
    }

    // ─── 503 Service Unavailable ──────────────────────────────────
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Map<String, Object>> handleServiceUnavailable(
            ServiceUnavailableException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(buildError(503, "Service Unavailable", ex.getMessage(), req.getRequestURI()));
    }

    // ─── 400 Bad Request — validasi @Valid gagal ──────────────────
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest req) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .findFirst()
                .orElse("Validasi gagal");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(400, "Bad Request", message, req.getRequestURI()));
    }

    // ─── 500 fallback — semua exception yang tidak tertangani ─────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(
            Exception ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(500, "Internal Server Error",
                        "Terjadi kesalahan internal", req.getRequestURI()));
    }
}
