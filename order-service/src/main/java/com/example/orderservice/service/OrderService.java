package com.example.orderservice.service;

import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.OrderStatusRequest;
import com.example.orderservice.dto.OrderStatusResponse;
import com.example.orderservice.dto.PlaceOrderRequest;

public interface OrderService {
    OrderResponse findOrderById(long orderId);

    OrderResponse placeOrder(PlaceOrderRequest placeOrderRequest);

    OrderStatusResponse setOrderStatus(long orderId, OrderStatusRequest orderStatusRequest);

    OrderStatusResponse getOrderStatus(long orderId);
}
