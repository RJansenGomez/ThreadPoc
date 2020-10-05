package org.example.rest;

import org.example.entity.Dish;
import org.example.entity.Order;
import org.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    ResponseEntity<String> createOrder(@RequestBody OrderCreationJsonRequest request) {
        return new ResponseEntity(orderService.createOrder(mapRequest(request)),
                HttpStatus.ACCEPTED);
    }

    private Order mapRequest(OrderCreationJsonRequest request) {
        return Order.builder()
                .id(UUID.randomUUID().toString())
                .dishesList(request.dishes.stream()
                        .map(this::mapDishes).collect(Collectors.toList())
                )
                .build();
    }

    private Dish mapDishes(OrderCreationJsonRequest.DishJson dishJson) {
        return Dish.builder()
                .id(dishJson.id)
                .build();
    }
}
