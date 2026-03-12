package com.example.order_service.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCommandInvoker {

    public <o> o invoke(OrderCommand<o> command) {
        log.info("[AUDIT] Executing command: {}", command.getClass().getSimpleName());
        long start = System.currentTimeMillis();

        try {
            o result = command.execute();
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