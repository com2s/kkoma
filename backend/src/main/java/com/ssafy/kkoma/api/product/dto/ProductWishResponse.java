package com.ssafy.kkoma.api.product.dto;

import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.WishList;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductWishResponse {

    private Long productId;
    private boolean wish;

    public static ProductWishResponse fromEntity(WishList wishList, Product product) {
        return ProductWishResponse.builder()
                .productId(product.getId())
                .wish(wishList.getIsValid())
                .build();
    }
}
