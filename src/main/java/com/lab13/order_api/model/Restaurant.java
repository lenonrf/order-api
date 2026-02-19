package com.lab13.order_api.model;

public class Restaurant {

    private String restaurantId;

    public Restaurant(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
