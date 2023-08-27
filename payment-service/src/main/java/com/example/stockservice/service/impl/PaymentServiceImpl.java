package com.example.stockservice.service.impl;

import com.example.stockservice.dto.PaymentRequest;
import com.example.stockservice.dto.UpdateBalanceRequest;
import com.example.stockservice.dto.UserResponse;
import com.example.stockservice.external.UserService;
import com.example.stockservice.repository.PaymentRepository;
import com.example.stockservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public String doPayment(PaymentRequest paymentRequest) {


        UserResponse sender = userService.getUserById(paymentRequest.getUserId()).getBody();
        UserResponse supplier = userService.getUserById(paymentRequest.getSupplierId()).getBody();


        /**
         * Hata dönerse nasıl bir exception gelecek?
         * bu exception payment service'de nasıl handle edilecek
         * */

        //sender'dan amount çıkar
        final double minusBalance = -paymentRequest.getAmount();
        String updatedSenderBalance = userService
                .updateBalance(
                        sender.getId(), UpdateBalanceRequest.builder().balance(minusBalance).build()
                )
                .getBody();


        //supplier'a amount ekle
        String updateSupplierBalance = userService
                .updateBalance(
                        supplier.getId(), UpdateBalanceRequest.builder().balance(paymentRequest.getAmount()).build()
                )
                .getBody();


        return "PAYMENT OK";
    }




}
