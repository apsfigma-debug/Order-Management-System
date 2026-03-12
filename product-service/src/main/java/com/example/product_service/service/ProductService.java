package com.example.product_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.product_service.dto.ProductRequest;
import com.example.product_service.dto.ProductResponse;
import com.example.product_service.entity.Product;
import com.example.product_service.exception.ProductNotFoundException;
import com.example.product_service.exception.InsufficientStockException;
import com.example.product_service.repository.ProductRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repo;

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return toResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse create(ProductRequest req) {
        Product product = new Product();
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());
        return toResponse(repo.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest req) {
        Product product = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());
        return toResponse(repo.save(product));
    }

    @Transactional
    public void delete(Long id) {
        Product product = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        repo.delete(product);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deductStock(Long id, int qty) throws InsufficientStockException {
        Product product = repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (product.getStock() < qty) {
            throw new InsufficientStockException(id, qty, product.getStock());
        }

        product.setStock(product.getStock() - qty);
        repo.save(product);
    }

    private ProductResponse toResponse(Product p) {
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .stock(p.getStock())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }

}
