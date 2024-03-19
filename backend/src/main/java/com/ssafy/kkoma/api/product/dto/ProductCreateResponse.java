package com.ssafy.kkoma.api.product.dto;

import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.ProductImage;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductCreateResponse {

    private List<String> productImages;
    private Integer categoryId;
    private String title;
    private String description;
    private int price;

    public static ProductCreateResponse fromEntity(Product product, List<ProductImage> productImages) {

        List<String> productImageUrls = productImages.stream()
                .map(ProductImage::getProductImage)
                .toList();

        return ProductCreateResponse.builder()
                .productImages(productImageUrls)
                .title(product.getTitle())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .price(product.getPrice())
                .build();
    }
}
