package com.lab13.order_api.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponse {

    private String restaurantId;
    private BigDecimal totalCommission;
    private int orderCount;
    private BigDecimal totalOrderValue;
    private BigDecimal averageOrderValue;
    private BigDecimal commissionRate;

    private List<OrderBreakdown> breakdowns;

    public OrderResponse() {
    }

    public OrderResponse(String restaurantId, BigDecimal totalCommisison, int orderCount, BigDecimal totalOrderValue,
            BigDecimal averageOrderValue, BigDecimal commissionRate, List<OrderBreakdown> breakdowns) {
        this.restaurantId = restaurantId;
        this.totalCommission = totalCommisison;
        this.orderCount = orderCount;
        this.totalOrderValue = totalOrderValue;
        this.averageOrderValue = averageOrderValue;
        this.commissionRate = commissionRate;
        this.breakdowns = breakdowns;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(BigDecimal totalCommisison) {
        this.totalCommission = totalCommisison;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getTotalOrderValue() {
        return totalOrderValue;
    }

    public void setTotalOrderValue(BigDecimal totalOrderValue) {
        this.totalOrderValue = totalOrderValue;
    }

    public BigDecimal getAverageOrderValue() {
        return averageOrderValue;
    }

    public void setAverageOrderValue(BigDecimal averageOrderValue) {
        this.averageOrderValue = averageOrderValue;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public List<OrderBreakdown> getBreakdowns() {
        return breakdowns;
    }

    public void setBreakdowns(List<OrderBreakdown> breakdowns) {
        this.breakdowns = breakdowns;
    }

    
}
