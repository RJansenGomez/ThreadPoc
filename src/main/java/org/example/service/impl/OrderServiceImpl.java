package org.example.service.impl;

import org.example.entity.Order;
import org.example.process.OrderProcessService;
import org.example.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderProcessService processService;

    public OrderServiceImpl(final OrderProcessService processService){
        this.processService = processService;
    }
    @Override
    public String createOrder(Order order) {
        return processService.startProcess(order);
    }


}
