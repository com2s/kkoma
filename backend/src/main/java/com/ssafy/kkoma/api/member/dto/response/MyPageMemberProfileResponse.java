package com.ssafy.kkoma.api.member.dto.response;

import com.ssafy.kkoma.api.product.dto.ProductSummary;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyPageMemberProfileResponse {

    private MemberProfileResponse memberProfileResponse;
    private List<ProductSummary> myProductList;

}
