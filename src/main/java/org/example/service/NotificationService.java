package org.example.service;

import org.example.entity.Dish;
import org.example.entity.Order;

public interface NotificationService {
    void notify(Order order, Dish dish);
}
