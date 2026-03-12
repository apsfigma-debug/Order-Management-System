package com.example.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockUpdateRequest {

    @NotNull
    @Min(value = 1, message = "Quantity minimal 1")
    private Integer quantity;
    private String stat;
}
