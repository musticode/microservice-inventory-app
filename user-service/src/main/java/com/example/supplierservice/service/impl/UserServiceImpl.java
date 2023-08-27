package com.example.supplierservice.service.impl;

import com.example.supplierservice.dto.UpdateBalanceRequest;
import com.example.supplierservice.dto.UserCreateRequest;
import com.example.supplierservice.dto.UserResponse;
import com.example.supplierservice.model.User;
import com.example.supplierservice.repository.UserRepository;
import com.example.supplierservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


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
    public String updateBalance(long userId, UpdateBalanceRequest updateBalanceRequest) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(
                        ()-> new RuntimeException("No user")
                );

//        10 - 20
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
