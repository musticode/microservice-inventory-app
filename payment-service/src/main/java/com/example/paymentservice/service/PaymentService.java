package com.example.paymentservice.service;

import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.model.Payment;

public interface PaymentService {
    String doPayment(PaymentRequest paymentRequest);

    Payment getPaymentById(long paymentId);
}
