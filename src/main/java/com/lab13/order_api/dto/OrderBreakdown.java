package com.lab13.order_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderBreakdown {

    private String orderId;
    private LocalDateTime timestamp;
    private BigDecimal orderValue;
    private BigDecimal commissionAmount;
    private BigDecimal commissionRate;

    
    public OrderBreakdown(String orderId, LocalDateTime timestamp, BigDecimal orderValue, BigDecimal commissionAmount,
            BigDecimal commissionRate) {
        this.orderId = orderId;
        this.timestamp = timestamp;
        this.orderValue = orderValue;
        this.commissionAmount = commissionAmount;
        this.commissionRate = commissionRate;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public BigDecimal getOrderValue() {
        return orderValue;
    }
    public void setOrderValue(BigDecimal orderValue) {
        this.orderValue = orderValue;
    }
    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }
    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }
    public BigDecimal getCommissionRate() {
        return commissionRate;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        result = prime * result + ((orderValue == null) ? 0 : orderValue.hashCode());
        result = prime * result + ((commissionAmount == null) ? 0 : commissionAmount.hashCode());
        result = prime * result + ((commissionRate == null) ? 0 : commissionRate.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderBreakdown other = (OrderBreakdown) obj;
        if (orderId == null) {
            if (other.orderId != null)
                return false;
        } else if (!orderId.equals(other.orderId))
            return false;
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        } else if (!timestamp.equals(other.timestamp))
            return false;
        if (orderValue == null) {
            if (other.orderValue != null)
                return false;
        } else if (!orderValue.equals(other.orderValue))
            return false;
        if (commissionAmount == null) {
            if (other.commissionAmount != null)
                return false;
        } else if (!commissionAmount.equals(other.commissionAmount))
            return false;
        if (commissionRate == null) {
            if (other.commissionRate != null)
                return false;
        } else if (!commissionRate.equals(other.commissionRate))
            return false;
        return true;
    }
    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    
}
