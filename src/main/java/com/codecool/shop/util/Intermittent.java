package com.codecool.shop.util;

import com.codecool.shop.model.Order;

public class Intermittent {
    private static Order order;

    public static Order getOrder() {
        return order;
    }

    public static void setOrder(Order order) {
        Intermittent.order = order;
    }
}
