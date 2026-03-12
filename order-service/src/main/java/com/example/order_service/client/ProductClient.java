package com.example.order_service.client;

import com.example.order_service.dto.ProductResponse;
import com.example.order_service.dto.StockUpdateRequest;

import com.example.order_service.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;

import javax.naming.ServiceUnavailableException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductClient {

    private final WebClient productServiceWebClient;

    public ProductResponse getProductById(Long productId) {

        return productServiceWebClient.get()
                .uri("/api/products/{id}", productId)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        res -> Mono.error(new ProductNotFoundException(productId)))
                .onStatus(status -> status.is5xxServerError(),
                        res -> Mono.error(new ServiceUnavailableException("Product Service error")))
                .bodyToMono(ProductResponse.class)
                .timeout(Duration.ofSeconds(10))
                .doOnError(e -> log.error("Error fetching product {}: {}", productId, e.getMessage()))
                .block();
    }

    public void deductStock(Long productId, int quantity, String stat) {
        productServiceWebClient.patch()
                .uri("/api/products/{id}/stock", productId)
                .bodyValue(new StockUpdateRequest(quantity, stat))
                .retrieve()
                .onStatus(status -> status.value() == 422,
                        res -> res.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new InsufficientStockException(productId, quantity, stat, 0))))
                .onStatus(status -> status.is5xxServerError(),
                        res -> Mono.error(new ServiceUnavailableException("Product Service down")))
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(10))
                .doOnError(e -> log.error("Stock deduction failed: {}", e.getMessage()))
                .block();
    }

    public void addstock(Long productId, int quantity, String stat) {
        productServiceWebClient.patch()
                .uri("/api/products/{id}/stock", productId)
                .bodyValue(new StockUpdateRequest(quantity, stat))
                .retrieve()
                .onStatus(status -> status.value() == 422,
                        res -> res.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new InsufficientStockException(productId, quantity, stat, 0))))
                .onStatus(status -> status.is5xxServerError(),
                        res -> Mono.error(new ServiceUnavailableException("Product Service down")))
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(10))
                .doOnError(e -> log.error("Stock update failed: {}", e.getMessage()))
                .block();

    }
}