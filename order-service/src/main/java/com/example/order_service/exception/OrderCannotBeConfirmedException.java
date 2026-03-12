package com.example.order_service.exception;

import com.example.order_service.entity.OrderStatus;

public class OrderCannotBeConfirmedException extends RuntimeException {
    public OrderCannotBeConfirmedException(Long id, OrderStatus currentStatus) {
        super("Order ID " + id + " tidak bisa di-confirm." +
                " Status saat ini: " + currentStatus +
                ". Hanya status PENDING yang bisa di-confirm.");
    }
}
