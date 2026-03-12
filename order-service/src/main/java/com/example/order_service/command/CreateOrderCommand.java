package com.example.order_service.command;

import com.example.order_service.client.ProductClient;
import com.example.order_service.controller.CreateOrderRequest;
import com.example.order_service.dto.*;

import com.example.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@RequiredArgsConstructor
@Setter
public class CreateOrderCommand implements OrderCommand<OrderResponse> {

    private final ProductClient productClient;
    private final OrderService serv;

    private CreateOrderRequest request;

    @Override
    public OrderResponse execute() {
        ProductResponse product = productClient.getProductById(request.getProductId());
        productClient.deductStock(request.getProductId(), request.getQuantity(), "");
        return serv.create(request, product);
    }
}
