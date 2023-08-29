package com.example.paymentservice.service.impl;



import com.example.orderservice.dto.order.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {


    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(OrderEvent orderEvent){
        System.out.println(orderEvent.getOrder().getId());
        System.out.println(orderEvent.getOrder().getQuantity());
        System.out.println(orderEvent.getOrder().getOrderDate());
        System.out.println("buyer: "+orderEvent.getOrder().getBuyerId());
        System.out.println("seller: "+orderEvent.getOrder().getSellerId());
    }

}