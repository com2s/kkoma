package com.ssafy.kkoma.api.member.service;

import com.ssafy.kkoma.api.member.dto.response.MemberInfoResponse;
import com.ssafy.kkoma.api.member.dto.request.UpdateMemberRequest;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.point.entity.Point;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    @Transactional
    void 회원_정보_수정() {

        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();

        Member savedMember = memberRepository.save(member);

        UpdateMemberRequest updateMemberRequest = UpdateMemberRequest.builder()
                        .name("lee")
                        .build();

        MemberInfoResponse memberInfoResponse = memberService.updateMemberInfo(savedMember.getId(), updateMemberRequest);

        assertEquals("lee", memberInfoResponse.getName());
    }

    @Test
    @Transactional
    void 포인트_생성() {
        // given
        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();

        Member savedMember = memberRepository.save(member);

        // when
        Point point = memberService.createPoint(savedMember.getId());
        Member foundMember = memberService.findMemberByMemberId(savedMember.getId());

        // then
        assertEquals(foundMember.getPoint().getId(), point.getId());
    }

    @Test
    @Transactional
    void 나의_판매글_조회() {
        // given
        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();
        Member savedMember = memberRepository.save(member);
        List<ProductSummary> myProductsBefore = memberService.getMyProducts(savedMember.getId());


        for (int i = 0; i < 10; i++) {
            Product product = Product.builder()
                    .title("...")
                    .status(ProductType.SALE)
                    .build();
            product.setMember(savedMember);
            productRepository.save(product);
        }
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder()
                    .title("...")
                    .status(ProductType.MID)
                    .build();
            product.setMember(savedMember);
            productRepository.save(product);
        }

        // when
        List<ProductSummary> myProducts = memberService.getMyProducts(savedMember.getId());

        // then
        assertEquals(myProductsBefore.size() + 10, myProducts.size());
    }

}
