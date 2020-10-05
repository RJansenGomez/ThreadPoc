package org.example.process;

import org.example.entity.Order;

public interface OrderProcessService {
    String startProcess(Order order);
}
