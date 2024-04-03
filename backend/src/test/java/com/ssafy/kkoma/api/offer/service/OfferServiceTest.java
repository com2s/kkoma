package com.ssafy.kkoma.api.offer.service;

import com.ssafy.kkoma.api.deal.dto.request.DecideOfferRequest;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.offer.dto.response.DecideOfferResponse;
import com.ssafy.kkoma.api.offer.dto.response.OfferResponse;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.offer.constant.OfferType;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.offer.entity.OfferDetail;
import com.ssafy.kkoma.domain.offer.repository.OfferDetailRepository;
import com.ssafy.kkoma.domain.offer.repository.OfferRepository;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.factory.CategoryFactory;
import com.ssafy.kkoma.factory.MemberFactory;
import com.ssafy.kkoma.factory.OfferFactory;
import com.ssafy.kkoma.factory.ProductFactory;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class OfferServiceTest {

    @Autowired
    MemberFactory memberFactory;
    @Autowired
    CategoryFactory categoryFactory;
    @Autowired
    OfferFactory offerFactory;
    @Autowired
    ProductFactory productFactory;

    @Autowired
    OfferRepository offerRepository;
    @Autowired
    OfferDetailRepository offerDetailRepository;

    @Autowired
    OfferService offerService;
    @Autowired
    MemberService memberService;

    @Test
    @Transactional
    public void 거래_요청_성공() throws Exception{
        // given
        Category category = categoryFactory.createCategory("목욕 용품");
        Member seller = memberFactory.createMember();
        Member buyer = memberFactory.createMember();
        buyer.getPoint().addBalance(20000);
        Product product = productFactory.createProduct(seller, category, 20000);

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
        Category category = categoryFactory.createCategory("목욕 용품");
        Member seller = memberFactory.createMember();
        Product product = productFactory.createProduct(seller, category, 20000);
        Member buyer = memberFactory.createMember();

        // when & then

        // 1) 잔액 부족으로 거래 요청 실패
        BusinessException lackingPointException = assertThrows(BusinessException.class, () -> {
            offerService.createOffer(buyer.getId(), product.getId());
        });
        assertEquals(lackingPointException.getErrorCode(), ErrorCode.POINT_NOT_ENOUGH);

        // 2) 존재하지 않는 상품글에 거래 요청
        buyer.getPoint().addBalance(20000);
        BusinessException invalidProductException = assertThrows(BusinessException.class, () -> {
            offerService.createOffer(buyer.getId(), -1000L);
        });
        assertEquals(invalidProductException.getErrorCode(), ErrorCode.PRODUCT_NOT_EXISTS);
    }

    @Test
    @Transactional
    public void 거래_요청_목록_조회() throws Exception{
        // given
        Category category = categoryFactory.createCategory("목욕 용품");
        Member seller = memberFactory.createMember();
        Product product = productFactory.createProduct(seller, category, 20000);

        Member buyer = memberFactory.createMember();
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
        Category category = categoryFactory.createCategory("목욕 용품");
        Member seller = memberFactory.createMember();
        Product product = productFactory.createProduct(seller, category, 0);

        Member befBuyer1 = memberFactory.createMember();
        Offer befOffer1 = offerFactory.createOffer(product, befBuyer1);

        Member befBuyer2 = memberFactory.createMember();
        Offer befOffer2 = offerFactory.createOffer(product, befBuyer2);
        int befBalance2 = befBuyer2.getPoint().getBalance();

        Member admin = memberFactory.createAdmin();

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

}
