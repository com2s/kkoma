package com.ssafy.kkoma.api.product.dto.hourly;

import com.ssafy.kkoma.api.product.dto.ProductSummary;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@SuperBuilder
public class ProductHourlyViewedResponse extends ProductSummary {

    @Setter
    private Long hourlyViewCount; // 지난 1시간 동안 몇 명의 사람들이 조회했는가

    public ProductHourlyViewedResponse(ProductHourlyWished p) {
        super(p.getId(), p.getThumbnailImage(), p.getTitle(), p.getStatus(),
                p.getPrice(), p.getWishCount(), p.getViewCount(), p.getOfferCount()
        );
        this.hourlyViewCount = p.getHourlyWishCount();
        this.dealPlace = p.getDealPlace();
        this.elapsedMinutes = p.getElapsedMinutes();
    }
}
