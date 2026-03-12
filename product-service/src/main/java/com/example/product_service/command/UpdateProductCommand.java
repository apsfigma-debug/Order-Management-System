package com.example.product_service.command;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.product_service.dto.ProductRequest;
import com.example.product_service.dto.ProductResponse;

import com.example.product_service.service.ProductService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Scope("prototype")
@Component
@Getter
@Setter
@RequiredArgsConstructor

public class UpdateProductCommand implements ProductCommand<ProductResponse> {

    private final ProductService serv;
    private ProductRequest request;
    private Long productId;

    @Override
    public ProductResponse execute() {
        return serv.update(productId, request);
    }

}
