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
public class ProductHourlyWishedResponse extends ProductSummary {

    @Setter
    private Long hourlyWishCount; // 지난 1시간 동안 몇 명의 사람들이 찜을 눌렀는가 (현재 13:00이라면, 12:00~12:59 생성된 row만 고려)

    public ProductHourlyWishedResponse (ProductHourlyWished p) {
        super(p.getId(), p.getThumbnailImage(), p.getTitle(), p.getStatus(),
                p.getPrice(), p.getWishCount(), p.getViewCount(), p.getOfferCount()
        );
        this.hourlyWishCount = p.getHourlyWishCount();
        this.dealPlace = p.getDealPlace();
        this.elapsedMinutes = p.getElapsedMinutes();
    }
}
