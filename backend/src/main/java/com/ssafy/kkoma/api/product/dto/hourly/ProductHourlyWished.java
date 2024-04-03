package com.ssafy.kkoma.api.product.dto.hourly;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@SuperBuilder
public class ProductHourlyWished extends ProductSummary {

    @Setter
    private Long hourlyWishCount; // 지난 1시간 동안 몇 명의 사람들이 찜을 눌렀는가 (현재 13:00이라면, 12:00~12:59 생성된 row만 고려)

    @QueryProjection
    public ProductHourlyWished(
        Long id, String thumbnailImage, String title, String dealPlace, ProductType status, int price,
        Long elapsedMinutes, Long wishCount, Long viewCount, Long offerCount, Long hourlyWishCount
    ) {
        super(id, thumbnailImage, title, dealPlace, status, price, elapsedMinutes, wishCount, viewCount, offerCount);
        this.hourlyWishCount = hourlyWishCount;
    }
}
