package com.example.order_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order_service.command.CancelOrderCommand;
import com.example.order_service.command.ConfirmOrderCommand;
import com.example.order_service.command.CreateOrderCommand;
import com.example.order_service.command.GetAllOrderCommand;
import com.example.order_service.command.GetOrderCommand;
import com.example.order_service.command.OrderCommandInvoker;
import com.example.order_service.dto.OrderResponse;

import java.util.List;

import org.springframework.context.ApplicationContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderCommandInvoker invoker;

    private final ApplicationContext context;

    @GetMapping({ "", "/" })
    public ResponseEntity<List<OrderResponse>> getAll() {
        GetAllOrderCommand command = context.getBean(GetAllOrderCommand.class);
        return ResponseEntity.ok(invoker.invoke(command));
    }

    @GetMapping({ "/{id}", "/" })
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        GetOrderCommand command = context.getBean(GetOrderCommand.class);
        command.setOrderId(id);
        return ResponseEntity.ok(invoker.invoke(command));
    }

    @PostMapping({ "", "/" })
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest req) {
        // Ambil instance baru (prototype) setiap request
        CreateOrderCommand command = context.getBean(CreateOrderCommand.class);
        command.setRequest(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoker.invoke(command));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancel(@PathVariable Long id, @RequestBody CreateOrderRequest req) {
        CancelOrderCommand command = context.getBean(CancelOrderCommand.class);
        command.setOrderId(id);
        command.setRequest(req);
        return ResponseEntity.ok(invoker.invoke(command));
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<OrderResponse> confirm(@PathVariable Long id) {
        ConfirmOrderCommand command = context.getBean(ConfirmOrderCommand.class);
        command.setOrderId(id);
        return ResponseEntity.ok(invoker.invoke(command));
    }
}
