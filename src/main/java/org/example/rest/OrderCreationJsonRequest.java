package org.example.rest;

import java.util.List;

public class OrderCreationJsonRequest {

    public List<DishJson> dishes;

    static class DishJson {
        public String id;
    }
}
