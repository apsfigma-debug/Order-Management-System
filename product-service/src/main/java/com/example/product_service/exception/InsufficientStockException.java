package com.example.product_service.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(Long id, int req, int avail) {
        super("Produk dengan id " + id + ". Diminta" + req + ". Kesediaan:" + avail);
    }
}
