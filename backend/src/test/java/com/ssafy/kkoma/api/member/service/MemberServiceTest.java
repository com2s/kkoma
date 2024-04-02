package com.ssafy.kkoma.api.member.service;

import com.ssafy.kkoma.api.member.dto.response.MemberInfoResponse;
import com.ssafy.kkoma.api.member.dto.request.UpdateMemberRequest;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.product.dto.ProductInfoResponse;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.domain.chat.entity.ChatRoom;
import com.ssafy.kkoma.domain.location.entity.Location;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.point.entity.Point;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;
import com.ssafy.kkoma.factory.AreaFactory;
import com.ssafy.kkoma.factory.ChatRoomFactory;
import com.ssafy.kkoma.factory.LocationFactory;

import org.junit.jupiter.api.Disabled;
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

    @Autowired
    ChatRoomFactory chatRoomFactory;

    @Autowired
    AreaFactory areaFactory;

    @Autowired
    LocationFactory locationFactory;

    @Test
    @Disabled(value = "Member name, email 삭제해야돼서 비활성화")
    @Transactional
    void 회원_정보_수정() {

//        Member member = Member.builder()
//                .name("kim")
//                .memberType(MemberType.KAKAO)
//                .role(Role.USER)
//                .build();
//
//        Member savedMember = memberRepository.save(member);
//
//        UpdateMemberRequest updateMemberRequest = UpdateMemberRequest.builder()
//                        .name("lee")
//                        .build();
//
//        MemberInfoResponse memberInfoResponse = memberService.updateMemberInfo(savedMember.getId(), updateMemberRequest);
//
//        assertEquals("lee", memberInfoResponse.getName());
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
        List<ProductInfoResponse> myProductsBefore = memberService.getMySellingProducts(savedMember.getId(), ProductType.SALE, ProductType.SOLD);
        Area area = areaFactory.createArea();

        for (int i = 0; i < 10; i++) {
            ChatRoom chatRoom = chatRoomFactory.createChatRoom();
            Location location = locationFactory.createLocation();
            Product product = Product.builder()
                    .title("...")
                    .status(ProductType.SALE)
                    .location(location)
                    .build();
            product.setMember(savedMember);
            product.setChatRoom(chatRoom);
            productRepository.save(product);
        }
        for (int i = 0; i < 10; i++) {
            ChatRoom chatRoom = chatRoomFactory.createChatRoom();
            Location location = locationFactory.createLocation();
            Product product = Product.builder()
                    .title("...")
                    .status(ProductType.PROGRESS)
                    .location(location)
                    .build();
            product.setMember(savedMember);
            product.setChatRoom(chatRoom);
            productRepository.save(product);
        }

        // when
        List<ProductInfoResponse> myProducts = memberService.getMySellingProducts(savedMember.getId(), ProductType.SALE, ProductType.SOLD);

        // then
        assertEquals(myProductsBefore.size() + 10, myProducts.size());
    }

}
