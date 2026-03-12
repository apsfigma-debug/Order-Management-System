package com.example.order_service.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Order dengan ID " + id + " tidak ditemukan");
    }
}
