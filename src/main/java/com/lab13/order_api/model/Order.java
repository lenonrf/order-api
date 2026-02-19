package com.lab13.order_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {

    private String orderId;
    private BigDecimal value;
    private LocalDateTime orderTime;

    private Restaurant restaurant;

    public Order(String orderId, BigDecimal value, LocalDateTime orderTime, Restaurant restaurant) {
        this.orderId = orderId;
        this.value = value;
        this.orderTime = orderTime;
        this.restaurant = restaurant;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
