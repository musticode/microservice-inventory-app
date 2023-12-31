package com.example.supplierservice.controller;

import com.example.supplierservice.dto.UpdateBalanceRequest;
import com.example.supplierservice.dto.UpdateOrderStatusRequest;
import com.example.supplierservice.dto.UserCreateRequest;
import com.example.supplierservice.dto.UserResponse;
import com.example.supplierservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest userCreateRequest){
        return new ResponseEntity<>(userService.createUser(userCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long userId){
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<String> loadUserByUserName(@PathVariable String username){
        return new ResponseEntity<>(userService.findByUserName(username), HttpStatus.OK);
    }

    @PostMapping("/update-balance/{userId}")
    public ResponseEntity<String> updateBalance(@PathVariable long userId, @RequestBody UpdateBalanceRequest updateBalanceRequest ){
        return new ResponseEntity<>(userService.updateBalance(userId, updateBalanceRequest), HttpStatus.OK);
    }

    @PostMapping("/update-order-status/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable long orderId,
                                                    @RequestBody UpdateOrderStatusRequest updateOrderStatusRequest
    ){

        return new ResponseEntity<>(userService.updateOrderStatus(orderId, updateOrderStatusRequest), HttpStatus.OK);

    }

}
