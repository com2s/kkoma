package com.ssafy.kkoma.api.product.dto.hourly;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@SuperBuilder
public class ProductHourlyWished extends ProductSummary {

    @Setter
    private Long hourlyWishCount;

    private Long regionCode;
    private LocalDateTime createdAt;

    @QueryProjection
    public ProductHourlyWished(
        Long id, String thumbnailImage, String title, ProductType status,
        int price, Long wishCount, Long viewCount, Long offerCount,
        Long hourlyWishCount, Long regionCode, LocalDateTime createdAt
    ) {
        super(id, thumbnailImage, title, status, price, wishCount, viewCount, offerCount);
        this.hourlyWishCount = hourlyWishCount;
        this.regionCode = regionCode;
        this.createdAt = createdAt;
    }
}
