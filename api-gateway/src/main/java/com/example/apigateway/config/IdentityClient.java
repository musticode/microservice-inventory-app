package com.example.apigateway.config;

import com.example.apigateway.model.AuthRequest;
import com.example.apigateway.model.UserCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "IDENTITY-SERVICE")
public interface IdentityClient {

    @PostMapping("/token")
    String getToken(@RequestBody AuthRequest authRequest);

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token);

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCreateRequest user);
}
