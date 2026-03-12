package com.example.order_service.command;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.order_service.controller.CreateOrderRequest;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.entity.Order;
import com.example.order_service.exception.OrderNotFoundException;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope("prototype")
@RequiredArgsConstructor
@Setter
public class ConfirmOrderCommand implements OrderCommand<OrderResponse> {

    private CreateOrderRequest request;
    private Long orderId;
    private final OrderService serv;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse execute() {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return serv.confirm(orderId, request, order);
    }
}
