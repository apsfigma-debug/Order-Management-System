package com.example.product_service.command;

public interface ProductCommand<p> {
    p execute();
}
