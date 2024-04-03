package com.ssafy.kkoma.api.product.dto;

import com.ssafy.kkoma.api.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductDetailResponse {

    private Long id;

    private List<String> productImages;

    private String categoryName;
    private String title;
    private String description;
    private int price;
    private Long elapsedMinutes;

    private Long chatRoomId;

    private ProductType status;

    private String dealPlace;
    private Double x;
    private Double y;

    private MemberSummaryResponse memberSummary;
    private boolean wish;

    private Long wishCount;
    private Long offerCount;
    private Long viewCount;

}
