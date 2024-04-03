package com.ssafy.kkoma.api.point.dto;

import com.ssafy.kkoma.domain.point.constant.PointChangeType;
import com.ssafy.kkoma.domain.point.entity.PointHistory;
import com.ssafy.kkoma.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Builder
public class PointHistorySummary {
    private Long id;
    private int amount;
    private int balanceAfterChange;
    private PointChangeType pointChangeType;
    private LocalDateTime date;
    private ProductInfo productInfo;

    @Setter
    @Getter
    static class ProductInfo {
        private Long id;
        private String thumbnail;
        private String title;

        public ProductInfo(Product product) {
            this.id = product.getId();
            this.thumbnail = product.getThumbnailImage();
            this.title = product.getTitle();
        }
    }

    public static PointHistorySummary fromEntity(PointHistory pointHistory) {
        return PointHistorySummary.builder()
            .id(pointHistory.getId())
            .amount(pointHistory.getAmount())
            .balanceAfterChange(pointHistory.getBalanceAfterChange())
            .pointChangeType(pointHistory.getPointChangeType())
            .date(pointHistory.getCreatedAt())
            .productInfo(pointHistory.getProduct() == null ? null : new ProductInfo(pointHistory.getProduct()))
            .build();
    }
}
