package com.example.order_service.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Order ID " + id + " tidak ditemukan");
    }
}