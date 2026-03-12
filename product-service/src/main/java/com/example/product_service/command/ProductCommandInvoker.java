package com.example.product_service.command;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductCommandInvoker {
    public <p> p invoke(ProductCommand<p> command) {
        log.info("[AUDIT] Executing command: {}", command.getClass().getSimpleName());
        long start = System.currentTimeMillis();

        try {
            p result = command.execute();
            log.info("[AUDIT] Command {} completed in {}ms",
                    command.getClass().getSimpleName(),
                    System.currentTimeMillis() - start);
            return result;
        } catch (Exception e) {
            log.error("[AUDIT] Command {} failed: {}",
                    command.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
    }
}
