package com.lab13.order_api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lab13.order_api.dto.OrderBreakdown;
import com.lab13.order_api.dto.OrderResponse;
import com.lab13.order_api.model.Order;
import com.lab13.order_api.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public OrderResponse getCommission(String restaurantId){

        OrderResponse response = new OrderResponse();
        List<Order> orders = repository.getOrdersByRestaurant(restaurantId);
        List<OrderBreakdown> breakdownList = new ArrayList<>();

        BigDecimal totalCommission = BigDecimal.ZERO;
        BigDecimal totalOrderValue = BigDecimal.ZERO;
        BigDecimal commissionRate = BigDecimal.ZERO;

        // Iterate Order List
        for (Order order : orders) {

            BigDecimal commissionAmount = getCommissionAmount(order.getValue());
            totalOrderValue = totalOrderValue.add(order.getValue());
            totalCommission = totalCommission.add(commissionAmount);
    
            // Create Breakdown Object
            OrderBreakdown breakdown = new OrderBreakdown(
                restaurantId, 
                order.getOrderTime(), 
                order.getValue(), 
                commissionAmount, 
                commissionAmount.divide(order.getValue(), 4, RoundingMode.HALF_UP)
            );

            breakdownList.add(breakdown);
            
        }

        // Calcular commission rate (taxa média): totalCommission / totalOrderValue
        commissionRate = totalCommission.divide(totalOrderValue, 4, RoundingMode.HALF_UP);

        response.setRestaurantId(restaurantId);
        response.setTotalCommission(totalCommission);
        response.setTotalOrderValue(totalOrderValue);
        response.setCommissionRate(commissionRate);
        response.setBreakdowns(breakdownList);

        BigDecimal averageOrderValue = totalOrderValue.divide(
            new BigDecimal(response.getBreakdowns().size()), 4, RoundingMode.HALF_UP
        );
        
        response.setAverageOrderValue(averageOrderValue);
        response.setOrderCount(response.getBreakdowns().size());

        return response;
    }



    private BigDecimal getCommissionAmount(BigDecimal orderValue){

        BigDecimal THRESHOULD_50 = new BigDecimal("50.00");
        BigDecimal THRESHOULD_200 = new BigDecimal("200.00");

        BigDecimal rate;

        if(orderValue.compareTo(THRESHOULD_50) < 0){
            rate = new BigDecimal("0.12");
        
        }else if(orderValue.compareTo(THRESHOULD_200) <= 0){
            rate = new BigDecimal("0.15");

        }else {
            rate = new BigDecimal("0.18");
        }

        return orderValue.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}
