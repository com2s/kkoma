package com.ssafy.kkoma.api.member.service;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.member.dto.response.MemberInfoResponse;
import com.ssafy.kkoma.api.member.dto.response.MemberProfileResponse;
import com.ssafy.kkoma.api.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.api.member.dto.response.MyPageMemberProfileResponse;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberService memberService;

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberService.findMemberByMemberId(memberId);
        return MemberInfoResponse.fromEntity(member);
    }

    public MemberSummaryResponse getMemberSummary(Long memberId) {
        Member member = memberService.findMemberByMemberId(memberId);
        return MemberSummaryResponse.fromEntity(member);
    }

    public MyPageMemberProfileResponse getMyPageMemberProfile(Long memberId) {
        Member member = memberService.findMemberByMemberId(memberId);
        List<Product> products = member.getProducts();

        return MyPageMemberProfileResponse.builder()
                .memberProfileResponse(MemberProfileResponse.fromEntity(member))
                .myProductList(products.stream().map(ProductSummary::fromEntity).toList())
                .build();
    }
}
