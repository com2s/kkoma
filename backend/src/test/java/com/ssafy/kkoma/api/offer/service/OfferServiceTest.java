package com.ssafy.kkoma.api.offer.service;

import com.ssafy.kkoma.api.deal.dto.request.DecideOfferRequest;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.offer.dto.response.OfferResponse;
import com.ssafy.kkoma.api.offer.dto.response.DecideOfferResponse;
import com.ssafy.kkoma.api.point.service.PointService;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.offer.constant.OfferType;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.offer.entity.OfferDetail;
import com.ssafy.kkoma.domain.offer.repository.OfferDetailRepository;
import com.ssafy.kkoma.domain.offer.repository.OfferRepository;
import com.ssafy.kkoma.domain.point.entity.Point;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.CategoryRepository;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class OfferServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OfferDetailRepository offerDetailRepository;

    @Autowired
    OfferService offerService;

    @Autowired
    MemberService memberService;

    @Autowired
    PointService pointService;

    @Test
    @Transactional
    public void 거래_요청_성공() throws Exception{
        // given
        Category category = createCategory("목욕 용품");
        Member seller = createMember("1@z.com");
        Member buyer = createMember("a@z.com");
        buyer.getPoint().addBalance(20000);
        Product product = createProduct(category, seller, 20000);

        // when
        Long offerId = offerService.createOffer(buyer.getId(), product.getId());
        Offer offer = offerService.findOfferByOfferId(offerId);

        // then
        assertEquals(OfferType.SENT, offer.getStatus());
    }

    @Test
    @Transactional
    public void 거래_요청_실패() throws Exception{
        // given
        Category category = createCategory("목욕 용품");
        Member seller = createMember("seller@z.com");
        Product product = createProduct(category, seller, 20000);
        Member buyer = createMember("fail@z.com");

        // when & then

        // 1) 잔액 부족으로 거래 요청 실패
        BusinessException lackingPointException = assertThrows(BusinessException.class, () -> {
            offerService.createOffer(buyer.getId(), product.getId());
        });
        assertEquals(lackingPointException.getErrorCode(), ErrorCode.POINT_NOT_ENOUGH);

        // 2) 존재하지 않는 상품글에 거래 요청
        buyer.getPoint().addBalance(20000);
        BusinessException invalidProductException = assertThrows(BusinessException.class, () -> {
            offerService.createOffer(buyer.getId(), 2L);
        });
        assertEquals(invalidProductException.getErrorCode(), ErrorCode.PRODUCT_NOT_EXISTS);
    }

    @Test
    @Transactional
    public void 거래_요청_목록_조회() throws Exception{
        // given
        Category category = createCategory("목욕 용품");
        Member seller = createMember("seller@z.com");
        Product product = createProduct(category, seller, 20000);

        Member buyer = createMember("buyer@z.com");
        Offer offer = offerRepository.save(Offer.builder().product(product).member(buyer).build());

        for (int i = 0; i < 3; i++) {
            OfferDetail offerDetail = OfferDetail.builder()
                .offerDate(LocalDate.now())
                .startTime(LocalTime.now())
                .endTime(LocalTime.now())
                .build();
            offerDetail.setOffer(offer);
            OfferDetail offerDetail1 = offerDetailRepository.save(offerDetail);
        }

        // when
        List<OfferResponse> offerResponseList = offerService.getOffers(product.getId());

        // then
        assertEquals(1, offerResponseList.size());
        assertEquals(3, offerResponseList.get(0).getOfferTimes().size());
    }

    @Test
    @Transactional
    public void 거래_요청_선택() throws Exception {
        // given
        Category category = createCategory("목욕 용품");
        Member seller = createMember("a@z.com");
        Product product = createProduct(category, seller, 0);

        Member befBuyer1 = createMember("b@z.com");
        Offer befOffer1 = createOffer(product, befBuyer1);

        Member befBuyer2 = createMember("c@z.com");
        Offer befOffer2 = createOffer(product, befBuyer2);
        int befBalance2 = befBuyer2.getPoint().getBalance();

        DecideOfferRequest decideOfferRequest = DecideOfferRequest.builder().selectedTime(LocalDateTime.now()).build();

        // when
        DecideOfferResponse acceptResult = offerService.acceptOffer(befOffer1.getId(), decideOfferRequest);

        // then
        assertNotNull(acceptResult.getDealTime());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            offerService.acceptOffer(befOffer2.getId(), decideOfferRequest);
        });
        assertEquals(exception.getErrorCode(), ErrorCode.INVALID_ACCEPT);

        Member aftBuyer2 = memberService.findMemberByMemberId(befBuyer2.getId());
        assertEquals(befBalance2 + product.getPrice(), aftBuyer2.getPoint().getBalance());
    }

    /*-----------------------------------------------------------------------------------------------------------------*/

    private Category createCategory(String name) {
        return categoryRepository.save(
            Category.builder().name(name).build()
        );
    }

    private Product createProduct(Category category, Member seller, int price) {
        return productRepository.save(
            Product.builder().title("상품명").thumbnailImage("썸네일")
                .category(category).member(seller).price(price).build()
        );
    }

    private Offer createOffer(Product product, Member buyer) {
        return offerRepository.save(
            Offer.builder().product(product).member(buyer).build()
        );
    }

    private Member createMember(String email) {
        return memberService.registerMember(
            Member.builder().name("temp").email(email).memberType(MemberType.KAKAO).role(Role.USER).build()
        );
    }

}
