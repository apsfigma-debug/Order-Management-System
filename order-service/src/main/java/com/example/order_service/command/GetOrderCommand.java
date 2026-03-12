package com.example.order_service.command;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.order_service.dto.OrderResponse;

import com.example.order_service.service.OrderService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope("prototype")
@RequiredArgsConstructor
@Getter
@Setter
public class GetOrderCommand implements OrderCommand<OrderResponse> {

    private final OrderService serv;
    private Long orderId;

    @Override
    public OrderResponse execute() {
        return serv.findById(orderId);
    }

}
