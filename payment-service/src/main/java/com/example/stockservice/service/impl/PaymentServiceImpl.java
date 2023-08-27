package com.example.stockservice.service.impl;

import com.example.orderservice.dto.OrderEvent;
import com.example.stockservice.dto.PaymentRequest;
import com.example.stockservice.dto.UpdateBalanceRequest;
import com.example.stockservice.dto.UserResponse;
import com.example.stockservice.external.UserService;
import com.example.stockservice.model.Payment;
import com.example.stockservice.repository.PaymentRepository;
import com.example.stockservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private UserService userService;
    private ModelMapper modelMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, UserService userService, ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }



    @Override
    public String doPayment(PaymentRequest paymentRequest) {
        UserResponse sender = userService.getUserById(paymentRequest.getUserId()).getBody();
        log.info("Sender: {}", sender.toString());

        UserResponse supplier = userService.getUserById(paymentRequest.getSupplierId()).getBody();
        log.info("Supplier: {}", supplier.toString());

        /**
         * Hata dönerse nasıl bir exception gelecek?
         * bu exception payment service'de nasıl handle edilecek
         * */

        //sender'dan amount çıkar
        final double minusBalance = -paymentRequest.getAmount();
        String updatedSenderBalance = userService
                .updateBalance(
                        sender.getId(),
                        UpdateBalanceRequest.builder().balance(minusBalance).build()
                )
                .getBody();
        log.info("Updated Sender balance: {}", updatedSenderBalance);

        //supplier'a amount ekle
        String updateSupplierBalance = userService
                .updateBalance(
                        supplier.getId(),
                        UpdateBalanceRequest.builder().balance(paymentRequest.getAmount()).build()
                )
                .getBody();
        log.info("updateSupplierBalance: {}", updateSupplierBalance);

        return "PAYMENT OK";
    }

    @Override
    public Payment getPaymentById(long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()-> new RuntimeException("No payment with id"));
        return payment;
    }


    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void setPaymentKafka(OrderEvent orderEvent){

        System.out.println("AAAAAAA"+ this.getClass().getName());

        System.out.println(orderEvent.getOrder().getId());
        System.out.println(orderEvent.getOrder().getQuantity());
        System.out.println(orderEvent.getOrder().getOrderDate());
        System.out.println("buyer: "+orderEvent.getOrder().getBuyerId());
        System.out.println("seller: "+orderEvent.getOrder().getSellerId());


        PaymentRequest paymentRequest = PaymentRequest.builder()
                .userId(orderEvent.getOrder().getBuyerId())
                .supplierId(orderEvent.getOrder().getSellerId())
                .amount(orderEvent.getOrder().getAmount())
                .build();
        String a = doPayment(paymentRequest);
        System.out.println(a);

    }




}
