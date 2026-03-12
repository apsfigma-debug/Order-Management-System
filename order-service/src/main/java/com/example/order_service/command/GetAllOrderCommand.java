package com.example.order_service.command;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.order_service.dto.OrderResponse;

import com.example.order_service.service.OrderService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@Scope("prototype")
@RequiredArgsConstructor
@Getter
public class GetAllOrderCommand implements OrderCommand<List<OrderResponse>> {

    private final OrderService serv;

    @Override
    public List<OrderResponse> execute() {

        return serv.findAll();
    }

}
