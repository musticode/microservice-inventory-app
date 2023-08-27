package com.example.stockservice.service;

import com.example.stockservice.dto.PaymentRequest;
import com.example.stockservice.model.Payment;

public interface PaymentService {
    String doPayment(PaymentRequest paymentRequest);

    Payment getPaymentById(long paymentId);
}
