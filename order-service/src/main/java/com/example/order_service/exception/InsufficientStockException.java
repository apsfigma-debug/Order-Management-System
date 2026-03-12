package com.example.order_service.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(Long productId, Integer requested, String stat, Integer available) {
        super("Stok tidak cukup untuk product ID " + productId +
                ". Diminta: " + requested + ", Tersedia: " + available);
    }

    public InsufficientStockException(Long productId, Integer requested, String stat) {
        super("Stok tidak cukup untuk product ID " + productId +
                ". Diminta: " + requested);
    }

    public InsufficientStockException(String message) {
        super(message);
    }
}