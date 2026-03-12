package com.example.product_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequest {

    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;
    private String description;

    @NotNull(message = "Harga harus diisi")
    @DecimalMin(value = "0.01", message = "Harga harus lebih dari 0")
    private BigDecimal price;

    @NotNull(message = "Stock harus diisi")
    @Min(value = 0, message = "Stock harus lebih dari 1")
    private Integer stock;

}
