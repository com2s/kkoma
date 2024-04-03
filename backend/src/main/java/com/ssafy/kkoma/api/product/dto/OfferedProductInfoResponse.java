package com.ssafy.kkoma.api.product.dto;

import java.time.Duration;
import java.time.LocalDateTime;

import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.domain.offer.constant.OfferType;
import com.ssafy.kkoma.domain.product.constant.MyProductType;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OfferedProductInfoResponse {

    private Long id;
    private String thumbnailImage;
    private String title;
    private String dealPlace;
    private ProductType status;
    private OfferType offerStatus;
    private int price;
    private Long elapsedMinutes;
    private Long wishCount;
    private Long viewCount;
    private Long offerCount;
    private MyProductType type;

    // 거래 관련
    private Long dealId;
    private LocalDateTime selectedTime;

    public static OfferedProductInfoResponse fromEntity(
            Product product, MyProductType type, OfferType offerType, Long dealId, LocalDateTime dealTime, Area area
    ) {
        LocalDateTime createdAt = product.getCreatedAt();
        Duration elapsedDuration = (createdAt != null) ? Duration.between(createdAt, LocalDateTime.now()) : null;

        return OfferedProductInfoResponse.builder()
                .id(product.getId())
                .thumbnailImage(product.getThumbnailImage())
                .title(product.getTitle())
                .status(product.getStatus())
                .dealPlace(area.getFullArea())
                .offerStatus(offerType)
                .price(product.getPrice())
                .elapsedMinutes((elapsedDuration != null) ? elapsedDuration.toMinutes() : null)
                .wishCount(product.getWishCount())
                .viewCount(product.getViewCount())
                .offerCount(product.getOfferCount())
                .type(type)
                .dealId(dealId)
                .selectedTime(dealTime)
                .build();
    }

}
