package com.lab13.order_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab13.order_api.dto.OrderRequest;
import com.lab13.order_api.dto.OrderResponse;
import com.lab13.order_api.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService service;

    @PostMapping("/commission")
    public ResponseEntity<OrderResponse> calculateCommission(@RequestBody OrderRequest request){
        log.info("Receive request to calculate a commission");

        OrderResponse response = service.getCommission(request.getRestaurantId());
        return ResponseEntity.ok(response);
    }
}
