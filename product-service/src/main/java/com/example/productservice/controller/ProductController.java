package com.example.productservice.controller;

import com.example.productservice.constant.ProductConstant;
import com.example.productservice.dto.ProductCreateRequest;
import com.example.productservice.dto.ProductQuantityRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(ProductConstant.PRODUCT_MAPPING)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @GetMapping(ProductConstant.PRODUCT_GET_BY_ID)
    public ResponseEntity<ProductResponse> getProductById(@PathVariable long productId){
        return new ResponseEntity<>(productService.findProductById(productId), HttpStatus.OK);
    }

    @GetMapping(ProductConstant.PRODUCT_QUANTITY_GET_BY_ID)
    public ResponseEntity<Integer> getProductQuantityByProductId(@PathVariable long productId){
        return new ResponseEntity<>(productService.findProductQuantityByProductId(productId), HttpStatus.OK);
    }

    @PostMapping("/product-quantity")
    public ResponseEntity<List<Integer>> getProductsQuantity(@RequestBody ProductQuantityRequest productQuantityRequest){
        return new ResponseEntity<>(productService.findProductsQuantities(productQuantityRequest), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return new ResponseEntity<>(productService.findProducts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> saveProduct(@RequestBody ProductCreateRequest productCreateRequest){
        return new ResponseEntity<>(productService.saveProduct(productCreateRequest), HttpStatus.CREATED);
    }

    @PutMapping(ProductConstant.PRODUCT_UPDATE_BY_ID)
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable long productId,@RequestBody ProductCreateRequest productCreateRequest){
        return new ResponseEntity<>(productService.updateProduct(productId, productCreateRequest), HttpStatus.OK);
    }

    @DeleteMapping(ProductConstant.PRODUCT_DELETE_BY_ID)
    public ResponseEntity<Boolean> updateProduct(@PathVariable long productId){
        boolean isDeleted = productService.deleteProductById(productId);
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }


    @PutMapping("/reduce-quantity/{id}")
    ResponseEntity<Void> reduceQuantity(@PathVariable long id, @RequestParam long quantity){
        return new ResponseEntity<>(productService.reduceQuantityById(id, quantity), HttpStatus.OK);
    }



}
