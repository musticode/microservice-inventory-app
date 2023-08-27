package com.example.supplierservice.service;

import com.example.supplierservice.dto.UpdateBalanceRequest;
import com.example.supplierservice.dto.UserCreateRequest;
import com.example.supplierservice.dto.UserResponse;

public interface UserService {
    UserResponse createUser(UserCreateRequest userCreateRequest);

    String updateBalance(long userId, UpdateBalanceRequest updateBalanceRequest);

    UserResponse findUserById(long userId);
}
