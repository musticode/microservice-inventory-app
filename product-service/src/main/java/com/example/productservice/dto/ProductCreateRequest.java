package com.example.productservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductCreateRequest {
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull
    private int quantity;
}
