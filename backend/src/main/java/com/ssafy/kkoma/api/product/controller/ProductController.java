package com.ssafy.kkoma.api.product.controller;

import com.ssafy.kkoma.api.product.dto.ProductDetailResponse;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Product")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Tag(name = "Product")
    @GetMapping("")
    public ResponseEntity<List<ProductSummary>> getProducts(){
        List<ProductSummary> products = productService.getProducts();

        return ResponseEntity.ok(products);
    }

    @Tag(name = "Product")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable Long productId){
        ProductDetailResponse productDetailResponse = productService.getProduct(productId);

        return ResponseEntity.ok(productDetailResponse);
    }

}
