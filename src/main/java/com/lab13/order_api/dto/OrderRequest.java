package com.lab13.order_api.dto;

import java.time.LocalDate;


public class OrderRequest {

    private String restaurantId;
    private LocalDate date;
    public String getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public OrderRequest(String restaurantId, LocalDate date) {
        this.restaurantId = restaurantId;
        this.date = date;
    }
}
