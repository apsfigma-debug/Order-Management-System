package com.example.order_service.command;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.order_service.client.ProductClient;
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
public class CancelOrderCommand implements OrderCommand<OrderResponse> {

    private final ProductClient productClient;
    private CreateOrderRequest request;
    private Long orderId;
    private final OrderService orderService;
    private OrderRepository orderRepository;

    @Override
    public OrderResponse execute() {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        productClient.addstock(request.getProductId(), request.getQuantity(), "CANCEL");
        return orderService.cancel(orderId, request, order);
    }
}
