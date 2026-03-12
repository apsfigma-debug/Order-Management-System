package com.example.order_service.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {

    @NotNull(message = "Product ID wajib diisi")
    private Long productId;

    @NotNull(message = "Quantity wajib diisi")
    @Min(value = 1, message = "Quantity minimal 1")
    private Integer quantity;
}
