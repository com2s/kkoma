package com.ssafy.kkoma.api.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.kkoma.api.member.dto.request.UpdateMemberRequest;
import com.ssafy.kkoma.api.member.dto.response.MemberInfoResponse;
import com.ssafy.kkoma.api.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.api.product.dto.OfferedProductInfoResponse;
import com.ssafy.kkoma.api.product.dto.ProductInfoResponse;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.offer.constant.OfferType;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.product.constant.MyProductType;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.factory.MemberFactory;
import com.ssafy.kkoma.factory.OfferFactory;
import com.ssafy.kkoma.factory.ProductFactory;
import com.ssafy.kkoma.global.util.CustomMockMvcSpringBootTest;
import com.ssafy.kkoma.global.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@Slf4j
@CustomMockMvcSpringBootTest
class MemberInfoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RequestUtil requestUtil;

    @Autowired
    MemberFactory memberFactory;

    @Autowired
    ProductFactory productFactory;

    @Autowired
    OfferFactory offerFactory;

    @Test
    void getMemberInfo() throws Exception {

        // 필요한 인스턴스 생성
        Member savedMember = memberFactory.createMember();

        // 예상 응답 인스턴스 생성
        MemberInfoResponse expectedMemberInfoResponse = MemberInfoResponse.fromEntity(savedMember);

        mockMvc.perform(
                        // 요청
                        // 해당 요청을 수행할 (로그인 된) 멤버를 매개변수로 전달 (로그인 안한 상황이면 비워두기)
                        requestUtil.getRequest("/api/members/info", savedMember)
                )
                .andExpectAll( // 예상 응답
                        // HTTP Status
                        MockMvcResultMatchers.status().isOk(),
                        // 반환 할 (data 속성 내에 들어갈) DTO 클래스와, 그 구현체를 인자로 전달
                        requestUtil.jsonContent(MemberInfoResponse.class, expectedMemberInfoResponse)
                );

        log.info(expectedMemberInfoResponse.toString());
    }

    @Test
    void getMemberSummary() throws Exception {

        Member savedMember = memberFactory.createMember();
        MemberSummaryResponse expectedResponse = MemberSummaryResponse.fromEntity(savedMember);

        mockMvc.perform(
                        requestUtil.getRequest("/api/members/summary", savedMember)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        requestUtil.jsonContent(MemberSummaryResponse.class, expectedResponse)
                );

        log.info(expectedResponse.toString());
    }

    @Test
    void updateMember() throws Exception {

        // 인스턴스 생성
        Member savedMember = memberFactory.createMember();

        // 예상 응답
        savedMember.setName("NEW_NAME");
        MemberInfoResponse expectedResponse = MemberInfoResponse.fromEntity(savedMember);

        // 요청
        UpdateMemberRequest request = UpdateMemberRequest.builder()
                .name("NEW_NAME")
                .build();

        mockMvc.perform(
                    // JSON과 함께 요청할 때
                    // 매개변수#3 : 요청 DTO 클래스
                    // 매개변수#4 : 요청 인스턴스
                    requestUtil.putRequestWithJson("/api/members", savedMember, UpdateMemberRequest.class, request)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        requestUtil.jsonContent(MemberInfoResponse.class, expectedResponse)
                );

        log.info(expectedResponse.toString());
    }

    @Test
    public void 구매글_목록_조회하기() throws Exception {

        // 인스턴스 생성
        Member member = memberFactory.createMember();
        Product soldProduct = productFactory.createProduct(member, ProductType.SOLD);
        Offer acceptedOffer = offerFactory.createOffer(soldProduct, member, OfferType.ACCEPTED);

        // 예상 응답
        OfferedProductInfoResponse expectedResponse = OfferedProductInfoResponse.fromEntity(soldProduct, MyProductType.BUY, acceptedOffer.getStatus(), null, null);

        mockMvc.perform(
                        requestUtil.getRequest("/api/members/products/buy", member)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        requestUtil.jsonListContent(OfferedProductInfoResponse.class, List.of(expectedResponse))
                );
    }

}