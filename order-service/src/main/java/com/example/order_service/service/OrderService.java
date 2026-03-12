package com.example.order_service.service;

import com.example.order_service.client.ProductClient;
import com.example.order_service.controller.CreateOrderRequest;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.dto.ProductResponse;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderStatus;
import com.example.order_service.exception.InsufficientStockException;
import com.example.order_service.exception.OrderCannotBeCancelledException;
import com.example.order_service.exception.OrderCannotBeConfirmedException;
import com.example.order_service.exception.OrderNotFoundException;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return toResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderResponse create(CreateOrderRequest request, ProductResponse product) {

        if (product.getStock() < request.getQuantity()) {
            throw new InsufficientStockException(
                    request.getProductId(), request.getQuantity(), "", product.getStock());
        }

        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(product.getPrice().multiply(
                java.math.BigDecimal.valueOf(request.getQuantity())));
        order.setStatus(OrderStatus.PENDING);

        return toResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse cancel(Long orderId, CreateOrderRequest request, Order order) {

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderCannotBeCancelledException(orderId, order.getStatus());
        }
        order.setStatus(OrderStatus.CANCELLED);

        Order savedOrder = orderRepository.save(order);
        return toResponse(savedOrder);
    }

    @Transactional
    public OrderResponse confirm(Long orderId, CreateOrderRequest request, Order order) {

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderCannotBeConfirmedException(orderId, order.getStatus());
        }

        order.setStatus(OrderStatus.CONFIRMED);

        return toResponse(orderRepository.save(order));
    }

    public OrderResponse toResponse(Order o) {
        return OrderResponse.builder()
                .id(o.getId())
                .productId(o.getProductId())
                .quantity(o.getQuantity())
                .totalPrice(o.getTotalPrice())
                .status(o.getStatus().name())
                .createdAt(o.getCreatedAt())
                .build();
    }
}
