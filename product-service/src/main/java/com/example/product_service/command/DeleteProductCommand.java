package com.example.product_service.command;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.product_service.service.ProductService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope("prototype")
@Getter
@Setter
@RequiredArgsConstructor
public class DeleteProductCommand implements ProductCommand<Void> {

    private final ProductService serv;
    private Long productId;

    @Override
    public Void execute() {
        serv.delete(productId);
        return null;
    }

}
