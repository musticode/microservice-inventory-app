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
    private final OrderProducerService orderProducerService;

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
    public OrderResponse placeOrder(PlaceOrderRequest placeOrderRequest) {

        // reduce quantity in client
        productClient.reduceQuantity(placeOrderRequest.getProductId(), placeOrderRequest.getQuantity());

        Order order = Order.builder()
                .productId(placeOrderRequest.getProductId())
                .quantity(placeOrderRequest.getQuantity())
                .orderDate(new Date())
                .amount(placeOrderRequest.getTotalAmount())
                .buyerId(placeOrderRequest.getBuyerId())
                .sellerId(placeOrderRequest.getSellerId())
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

        // TODO : Order is sent to topic
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order is pending state");
        orderEvent.setOrder(order);

        orderProducerService.sendMessage(orderEvent);

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
