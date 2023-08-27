package com.example.productservice.service;

import com.example.productservice.dto.ProductCreateRequest;
import com.example.productservice.dto.ProductQuantityRequest;
import com.example.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse findProductById(long productId);

    List<ProductResponse> findProducts();

    ProductResponse saveProduct(ProductCreateRequest productCreateRequest);

    ProductResponse updateProduct(long productId, ProductCreateRequest productCreateRequest);

    boolean deleteProductById(long productId);

    int findProductQuantityByProductId(long productId);

    List<Integer> findProductsQuantities(ProductQuantityRequest productQuantityRequest);

    Void reduceQuantityById(long id, long quantity);
}
