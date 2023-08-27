package com.example.stockservice.service;

import com.example.stockservice.dto.PaymentRequest;

public interface PaymentService {
    String doPayment(PaymentRequest paymentRequest);
}
