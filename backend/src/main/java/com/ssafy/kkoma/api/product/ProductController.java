package com.ssafy.kkoma.api.product;

import com.ssafy.kkoma.domain.product.dto.response.ProductDetailResponse;
import com.ssafy.kkoma.domain.product.dto.response.ProductSummary;
import com.ssafy.kkoma.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<List<ProductSummary>> getProducts(){
        List<ProductSummary> products = productService.getProducts();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable Long productId){
        ProductDetailResponse productDetailResponse = productService.getProduct(productId);

        return ResponseEntity.ok(productDetailResponse);
    }

}
