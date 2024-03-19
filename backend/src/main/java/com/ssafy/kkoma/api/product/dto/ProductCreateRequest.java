package com.ssafy.kkoma.api.product.dto;

import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.ProductImage;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductCreateRequest {

    private List<String> productImages;
    private Integer categoryId;
    private String title;
    private String description;
    private int price;

}
