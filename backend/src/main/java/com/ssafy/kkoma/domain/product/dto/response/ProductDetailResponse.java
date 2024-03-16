package com.ssafy.kkoma.domain.product.dto.response;

import com.ssafy.kkoma.domain.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductDetailResponse {

    private Long id;
    private List<String> productImages;
    private String title;
    private String description;
    private String categoryName;
    private int price;
    private ProductType condition;
    private String dealPlace;
    private Long elapsedMinutes;
    private MemberSummaryResponse memberSummary;
    private Long wishCount;
    private Long offerCount;
    private Long viewCount;

}
