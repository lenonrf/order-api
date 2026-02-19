package com.lab13.order_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lab13.order_api.dto.OrderResponse;
import com.lab13.order_api.model.Order;
import com.lab13.order_api.model.Restaurant;
import com.lab13.order_api.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderService service;

    @BeforeEach
    void setUp(){
        
    }

    @Test
    void shouldGetOrderListByRestaurantId(){

        // ARRANGE
        List<Order> orders = List.of(
            new Order("ORD001", BigDecimal.valueOf(150.00), LocalDateTime.of(2026, 2, 18, 12, 30), new Restaurant("R001")),
            new Order("ORD002", BigDecimal.valueOf(85.50), LocalDateTime.of(2026, 2, 18, 13, 15), new Restaurant("R001")),
            new Order("ORD003", BigDecimal.valueOf(220.00), LocalDateTime.of(2026, 2, 18, 19, 45), new Restaurant("R001")),
            new Order("ORD004", BigDecimal.valueOf(95.00), LocalDateTime.of(2026, 2, 18, 20, 10), new Restaurant("R001")),
            new Order("ORD005", BigDecimal.valueOf(175.00), LocalDateTime.of(2026, 2, 18, 21, 30), new Restaurant("R001"))

        );

        // ACT
        when(repository.getOrdersByRestaurant("R001")).thenReturn(orders);
        OrderResponse response = service.getCommission("R001");
        
        // ASSERT - Valores calculados baseados no mock
        // ORD001: $150.00 x 15% = $22.50
        // ORD002: $85.50 x 15% = $12.83
        // ORD003: $220.00 x 18% = $39.60 (>$200)
        // ORD004: $95.00 x 15% = $14.25
        // ORD005: $175.00 x 15% = $26.25
        // Total: $115.43
        
        assertNotNull(response);
        assertEquals("R001", response.getRestaurantId());
        assertEquals(5, response.getOrderCount());
        
        // Total Order Value = 150 + 85.50 + 220 + 95 + 175 = $725.50
        assertEquals(0, new BigDecimal("725.50").compareTo(response.getTotalOrderValue()));
        
        // Total Commission = $115.43 (com typo no nome do método: getTotalCommisison)
        assertEquals(0, new BigDecimal("115.43").compareTo(response.getTotalCommission()));
        
        // Average Order Value = 725.50 / 5 = $145.10
        assertEquals(0, new BigDecimal("145.10").compareTo(response.getAverageOrderValue()));
        
        // Average Commission Rate = 115.43 / 725.50 ≈ 0.1591
        assertEquals(0, new BigDecimal("0.16").compareTo(response.getCommissionRate().setScale(2, java.math.RoundingMode.HALF_UP)));
        
        // Verificar que breakdown tem 5 itens
        assertNotNull(response.getBreakdowns());
        assertEquals(5, response.getBreakdowns().size());

    }

}
