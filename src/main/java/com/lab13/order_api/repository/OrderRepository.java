package com.lab13.order_api.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lab13.order_api.model.Order;
import com.lab13.order_api.model.Restaurant;

@Service
public class OrderRepository {

    private static List<Order> orders = List.of(
            new Order("ORD001", BigDecimal.valueOf(150.00), LocalDateTime.of(2026, 2, 18, 12, 30), new Restaurant("R001")),
            new Order("ORD002", BigDecimal.valueOf(85.50), LocalDateTime.of(2026, 2, 18, 13, 15), new Restaurant("R001")),
            new Order("ORD003", BigDecimal.valueOf(220.00), LocalDateTime.of(2026, 2, 18, 19, 45), new Restaurant("R001")),
            new Order("ORD004", BigDecimal.valueOf(95.00), LocalDateTime.of(2026, 2, 18, 20, 10), new Restaurant("R001")),
            new Order("ORD005", BigDecimal.valueOf(175.00), LocalDateTime.of(2026, 2, 18, 21, 30), new Restaurant("R001")),
            new Order("ORD006", BigDecimal.valueOf(120.00), LocalDateTime.of(2026, 2, 18, 12, 00), new Restaurant("R002")),
            new Order("ORD007", BigDecimal.valueOf(65.00), LocalDateTime.of(2026, 2, 18, 14, 30), new Restaurant("R002")),
            new Order("ORD008", BigDecimal.valueOf(180.00), LocalDateTime.of(2026, 2, 18, 19, 00), new Restaurant("R002"))
        );

    public List<Order> getOrdersByRestaurant(String restaurantId){

        return orders.stream()
            .filter(order -> order.getRestaurant().getRestaurantId().equals(restaurantId))
            .toList();
    }
}
