package com.ssafy.kkoma.api.product.service;

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

    public List<String> getProductImages(Long productId){
        List<ProductImage> productImages = productImageRepository.findAllProductImagesByProductId(productId);
        List<String> images = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            images.add(productImage.getProductImage());
        }

        return images;
    }
}
