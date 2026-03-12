package com.example.product_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockUpdateRequest {
    @NotNull
    private Integer quantity;
    private String stat;

}
