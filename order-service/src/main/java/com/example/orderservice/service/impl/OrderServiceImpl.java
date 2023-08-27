package com.example.orderservice.service.impl;

import com.example.orderservice.dto.*;
import com.example.orderservice.external.ProductClient;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderLineItem;
import com.example.orderservice.repository.OrderLineItemRepository;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final OrderLineItemRepository orderLineItemRepository;
    private final ModelMapper modelMapper;
    private final ProductClient productClient;

    @Override
    public OrderResponse findOrderById(long orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(
                        ()-> new RuntimeException("No order")
                );

        return mapToOrderResponse(order);
    }



    @Override
    public OrderResponse placeOrder(PlaceOrderRequest placeOrderRequest) { //




        // order req:    private long productId;
        //    private long totalAmount;
        //    private long quantity;
        //    private PaymentMode paymentMode;

        // reduce quantity in client
        productClient.reduceQuantity(placeOrderRequest.getProductId(), placeOrderRequest.getQuantity());

        Order order = Order.builder()
                .productId(placeOrderRequest.getProductId())
                .quantity(placeOrderRequest.getQuantity())
                .orderDate(new Date())
                .orderStatus("ORDERED")
                .amount(placeOrderRequest.getTotalAmount())
                .build();

        // order save         // TODO order entity -> save the data with status order created
        //        Order order = Order.builder()
        //                .amount(orderRequest.getTotalAmount())
        //                .orderStatus("CREATED")
        //                .productId(orderRequest.getProductId())
        //                .orderDate(Instant.now())
        //                .quantity(orderRequest.getQuantity())
        //                .build();
        //
        //        order = orderRepository.save(order);



        // payment service --> doPayment [user'dan al, supplier'a ver kendi içinde farklı client'lar olmalı]



        return mapToOrderResponse(order);
    }


    private OrderResponse mapToOrderResponse(Order order){
        return modelMapper.map(order, OrderResponse.class);
    }

    private OrderLineItemResponse mapToOrderLineItemResponse(OrderLineItem orderLineItem){
        return modelMapper.map(orderLineItem, OrderLineItemResponse.class);
    }
}
