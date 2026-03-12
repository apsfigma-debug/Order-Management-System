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
@Getter
@Setter
@Component
@RequiredArgsConstructor
public class CreateProductCommand implements ProductCommand<ProductResponse> {

    private final ProductService serv;
    private ProductRequest request;

    @Override
    public ProductResponse execute() {
        return serv.create(request);
    }

}
