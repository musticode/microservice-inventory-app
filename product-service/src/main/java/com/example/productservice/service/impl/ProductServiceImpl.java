package com.example.productservice.service.impl;

import com.example.productservice.dto.ProductCreateRequest;
import com.example.productservice.dto.ProductQuantityRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponse findProductById(long productId) {

        Product product = productRepository
                .findById(productId)
                .orElseThrow(
                        ()-> new ProductNotFoundException("No product")
                );
        return mapToProductResponse(product);
    }

    @Override
    public List<ProductResponse> findProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse saveProduct(ProductCreateRequest productCreateRequest) {
        Product product = productRepository
                .save(
                        Product.builder()
                                .name(productCreateRequest.getName())
                                .description(productCreateRequest.getDescription())
                                .price(productCreateRequest.getPrice())
                                .quantity(productCreateRequest.getQuantity())
                                .build()
                );

        return mapToProductResponse(product);
    }

    @Override
    public ProductResponse updateProduct(long productId, ProductCreateRequest productCreateRequest) {

        Product product = productRepository
                .findById(productId)
                .orElseThrow(
                        ()-> new ProductNotFoundException("No product")
                );

        product.setName(productCreateRequest.getName());
        product.setDescription(productCreateRequest.getDescription());
        product.setPrice(productCreateRequest.getPrice());
        product.setQuantity(productCreateRequest.getQuantity());
        Product updated = productRepository.save(product);

        return mapToProductResponse(updated);
    }

    @Override
    public boolean deleteProductById(long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(
                        ()-> new ProductNotFoundException("No product")
                );
        productRepository.delete(product);
        return true;
    }

    @Override
    public int findProductQuantityByProductId(long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(
                        ()-> new ProductNotFoundException("No product")
                );

        return product.getQuantity();
    }

    @Override
    public List<Integer> findProductsQuantities(ProductQuantityRequest productQuantityRequest) {

        List<Integer> productQuantityList = new ArrayList<>();

        for (int i = 0; i < productQuantityRequest.getProductIdList().size(); i++){
            if (findProductById((long) i).getQuantity() < 0){
                throw new RuntimeException("Not enough quantity");
            }
            productQuantityList.add(findProductById((long) i).getQuantity());
        }

        return productQuantityList;
    }

    @Override
    public Void reduceQuantityById(long id, long quantity) {

        Product product = productRepository
                .findById(id)
                .orElseThrow(
                        ()-> new ProductNotFoundException("No product")
                );
        //10- 20
        if (quantity < 0 && (product.getQuantity()-quantity < 0)){
            throw new RuntimeException("Not valid quantity");
        }
        product.setQuantity((int)quantity);
        productRepository.save(product);

        return null;
    }


    private ProductResponse mapToProductResponse(Product product){
        return modelMapper.map(product, ProductResponse.class);
    }

}
