package com.ssafy.kkoma.api.product.service;

import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.ProductImage;
import com.ssafy.kkoma.domain.product.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public List<String> getProductImageUrls(Long productId){
        List<ProductImage> productImages = productImageRepository.findAllProductImagesByProductId(productId);
        List<String> images = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            images.add(productImage.getProductImage());
        }

        return images;
    }

    public List<ProductImage> createProductImages(List<String> productImageUrls, Product product) {
        List<ProductImage> ret = new ArrayList<>();
        for (String productImageUrl : productImageUrls) {
            ProductImage productImage = ProductImage.builder().productImage(productImageUrl).product(product).build();
            ProductImage savedProductImage = productImageRepository.save(productImage);
            ret.add(savedProductImage);
        }
        return ret;
    }

}
