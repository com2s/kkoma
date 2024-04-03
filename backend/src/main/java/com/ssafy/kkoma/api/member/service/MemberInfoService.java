package com.ssafy.kkoma.api.member.service;

import com.ssafy.kkoma.api.area.service.AreaService;
import com.ssafy.kkoma.api.member.dto.request.UpdateMemberPreferredPlaceRequest;
import com.ssafy.kkoma.api.member.dto.response.*;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberService memberService;
    private final AreaService areaService;

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberService.findMemberByMemberId(memberId);
        return MemberInfoResponse.fromEntity(member);
    }

    public MemberSummaryResponse getMemberSummary(Long memberId) {
        Member member = memberService.findMemberByMemberId(memberId);
        Area area = areaService.findAreaById(member.getPreferredPlaceRegionCode());
        log.info("###" + member.getPreferredPlaceRegionCode() + ": " + area.getId());

        MemberSummaryResponse memberSummaryResponse = MemberSummaryResponse.fromEntity(member);
        memberSummaryResponse.setPreferredPlace(area.getFullArea());
        return memberSummaryResponse;
    }

    public MyPageMemberProfileResponse getMyPageMemberProfile(Long memberId) {
        Member member = memberService.findMemberByMemberId(memberId);
        List<Product> products = member.getProducts();

        return MyPageMemberProfileResponse.builder()
                .memberProfileResponse(MemberProfileResponse.fromEntity(member))
                .myProductList(products.stream().map(ProductSummary::fromEntity).toList())
                .build();
    }

    public MemberPreferredPlaceResponse updateMemberPreferredPlace(Long memberId, UpdateMemberPreferredPlaceRequest updateMemberPreferredPlaceRequest) {
        Member member = memberService.findMemberByMemberId(memberId);
        member.setPreferredPlaceRegionCode(updateMemberPreferredPlaceRequest.getPreferredPlaceRegionCode());
        return MemberPreferredPlaceResponse.builder()
                .preferredPlaceRegionCode(member.getPreferredPlaceRegionCode())
                .build();
    }

    public MemberPreferredPlaceResponse getMemberPreferredPlace(Long memberId) {
        Member member = memberService.findMemberByMemberId(memberId);

        return MemberPreferredPlaceResponse.builder()
                .preferredPlaceRegionCode(member.getPreferredPlaceRegionCode())
                .build();
    }

}
