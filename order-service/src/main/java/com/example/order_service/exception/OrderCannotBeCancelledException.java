package com.example.order_service.exception;

import com.example.order_service.entity.OrderStatus;

public class OrderCannotBeCancelledException extends RuntimeException {
    public OrderCannotBeCancelledException(Long id, OrderStatus currentStatus) {
        super("Order ID " + id + " tidak bisa di-cancel." +
                " Status saat ini: " + currentStatus +
                ". Hanya status PENDING yang bisa di-cancel.");
    }
}