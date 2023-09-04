package com.example.supplierservice.client;

import com.example.supplierservice.dto.order.OrderStatusRequest;
import com.example.supplierservice.dto.order.OrderStatusResponse;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient {

    @PostMapping("/order-status/{orderId}")
    ResponseEntity<OrderStatusResponse> setOrderStatus(@PathVariable long orderId,
                                                              @RequestBody OrderStatusRequest orderStatusRequest
    );
}
