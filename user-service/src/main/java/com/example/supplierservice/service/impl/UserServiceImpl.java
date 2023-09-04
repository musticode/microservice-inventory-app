package com.example.supplierservice.service.impl;

import com.example.supplierservice.client.OrderClient;
import com.example.supplierservice.dto.UpdateBalanceRequest;
import com.example.supplierservice.dto.UpdateOrderStatusRequest;
import com.example.supplierservice.dto.UserCreateRequest;
import com.example.supplierservice.dto.UserResponse;
import com.example.supplierservice.dto.order.OrderStatusRequest;
import com.example.supplierservice.dto.order.OrderStatusResponse;
import com.example.supplierservice.model.User;
import com.example.supplierservice.repository.UserRepository;
import com.example.supplierservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final OrderClient orderClient;

    @Override
    public UserResponse createUser(UserCreateRequest userCreateRequest) {

        User user = User.builder()
                .name(userCreateRequest.getName())
                .surname(userCreateRequest.getSurname())
                .balance(0)
                .build();

        userRepository.save(user);

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public String updateOrderStatus(long orderId, UpdateOrderStatusRequest updateOrderStatusRequest) {
        OrderStatusRequest orderStatusRequest = OrderStatusRequest
                .builder()
                .orderStatus(updateOrderStatusRequest.getOrderStatus())
                .build();

        OrderStatusResponse orderStatusResponse = orderClient
                .setOrderStatus(
                        orderId,
                        orderStatusRequest
                )
                .getBody();
        log.info("Order status: {}", updateOrderStatusRequest.getOrderStatus());

        return orderStatusResponse.getOrderStatus();
    }

    @Override
    public String findByUserName(String username) {
        User user = userRepository.findByName(username).get();
        return user.getName();
    }

    @Override
    public String updateBalance(long userId, UpdateBalanceRequest updateBalanceRequest) {

        log.info("user id {}", userId);

        User user = userRepository
                .findById(userId)
                .orElseThrow(
                        ()-> new RuntimeException("No user")
                );

        if (updateBalanceRequest.getBalance() < 0 && (user.getBalance() - updateBalanceRequest.getBalance() < 0) ){
            throw new RuntimeException("Not enough balance");
        }

        user.setBalance(updateBalanceRequest.getBalance());
        userRepository.save(user);

        return "UPDATED";
    }

    @Override
    public UserResponse findUserById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("No user"));
        return modelMapper.map(user, UserResponse.class);
    }
}
