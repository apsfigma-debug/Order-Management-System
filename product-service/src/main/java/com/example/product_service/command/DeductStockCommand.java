package com.example.product_service.command;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.product_service.dto.StockUpdateRequest;
import com.example.product_service.service.ProductService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Scope("prototype")
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j

public class DeductStockCommand implements ProductCommand<Void> {

    private final ProductService serv;
    private Long productId;
    private StockUpdateRequest request;

    @Override
    public Void execute() {
        serv.deductStock(productId, request.getQuantity());
        return null;
    }
}
