package com.example.stockservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private long userId;
    private String userName;
    private String userSurname;
    private long supplierId;
    private String supplierName;
    private String supplierSurname;
    private Date date;
    private double amount;
}
