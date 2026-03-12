package com.example.product_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product_service.command.CreateProductCommand;
import com.example.product_service.command.DeductStockCommand;
import com.example.product_service.command.DeleteProductCommand;
import com.example.product_service.command.GetAllProductCommand;
import com.example.product_service.command.GetProductCommand;
import com.example.product_service.command.ProductCommandInvoker;
import com.example.product_service.command.UpdateProductCommand;
import com.example.product_service.dto.ProductRequest;
import com.example.product_service.dto.ProductResponse;
import com.example.product_service.dto.StockUpdateRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.context.ApplicationContext;

@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductCommandInvoker invoker;
    private final ApplicationContext context;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        GetProductCommand command = context.getBean(GetProductCommand.class);
        command.setProductId(id);
        return ResponseEntity.ok(invoker.invoke(command));
    }

    @GetMapping({ "", "/" })
    public ResponseEntity<List<ProductResponse>> getAll() {
        GetAllProductCommand command = context.getBean(GetAllProductCommand.class);
        return ResponseEntity.ok(invoker.invoke(command));
    }

    @PostMapping({ "", "/" })
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest req) {
        CreateProductCommand command = context.getBean(CreateProductCommand.class);
        command.setRequest(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoker.invoke(command));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,
            @RequestBody ProductRequest req) {
        UpdateProductCommand command = context.getBean(UpdateProductCommand.class);
        command.setProductId(id);
        command.setRequest(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoker.invoke(command));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> updateStock(@PathVariable Long id,
            @Valid @RequestBody StockUpdateRequest req) {
        DeductStockCommand command = context.getBean(DeductStockCommand.class);
        command.setProductId(id);
        command.setRequest(req);
        invoker.invoke(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        DeleteProductCommand command = context.getBean(DeleteProductCommand.class);
        command.setProductId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoker.invoke(command));
    }
}
