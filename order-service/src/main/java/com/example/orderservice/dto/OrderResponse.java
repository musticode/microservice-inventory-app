package com.example.orderservice.dto;

import com.example.orderservice.model.OrderLineItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private long id;
    List<OrderLineItemResponse> orderLineItems;
}
