package com.example.product_service.command;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.product_service.dto.ProductResponse;

import com.example.product_service.service.ProductService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope("prototype")
@RequiredArgsConstructor
@Getter
@Setter
public class GetProductCommand implements ProductCommand<ProductResponse> {

    private final ProductService serv;
    private Long productId;

    @Override
    public ProductResponse execute() {
        return serv.findById(productId);
    }

}
