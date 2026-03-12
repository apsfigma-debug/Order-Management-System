package com.example.product_service.command;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.product_service.dto.ProductResponse;

import com.example.product_service.service.ProductService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@Getter
@Scope("prototype")
@RequiredArgsConstructor
public class GetAllProductCommand implements ProductCommand<List<ProductResponse>> {

    private final ProductService serv;

    @Override
    public List<ProductResponse> execute() {
        return serv.findAll();
    }

}
