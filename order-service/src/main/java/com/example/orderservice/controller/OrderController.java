package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.PlaceOrderRequest;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {


    private final OrderService orderService;
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable long orderId){
        return new ResponseEntity<>(orderService.findOrderById(orderId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest){
        return new ResponseEntity<>(orderService.placeOrder(placeOrderRequest), HttpStatus.OK);
    }
}
