package com.example.order_service.command;


public interface OrderCommand<o> {

o execute();
}
