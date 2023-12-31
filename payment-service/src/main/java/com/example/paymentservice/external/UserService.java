package com.example.paymentservice.external;

import com.example.paymentservice.dto.UpdateBalanceRequest;
import com.example.paymentservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "USER-SERVICE", url = "http://localhost:8085/api/v1/users")
public interface UserService {

    @GetMapping("/{userId}")
    ResponseEntity<UserResponse> getUserById(@PathVariable long userId);

    @PostMapping("/update-balance/{userId}")
    ResponseEntity<String> updateBalance(@PathVariable long userId, @RequestBody UpdateBalanceRequest updateBalanceRequest);

}
