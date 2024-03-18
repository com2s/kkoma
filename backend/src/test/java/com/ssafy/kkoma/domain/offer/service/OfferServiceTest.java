package com.ssafy.kkoma.domain.offer.service;

import com.ssafy.kkoma.api.offer.service.OfferService;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.offer.constant.OfferType;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.CategoryRepository;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OfferServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OfferService offerService;

    private static final String TITLE = "TITLE";
    private static final String IMAGE_URL = "IMAGE_URL";
    private static final String NAME = "NAME";

    @Test
    @Transactional
    public void 거래_요청_성공() throws Exception{
        // given
        Category category = categoryRepository.save(Category.builder().name("유모차").build());
        Member member = memberRepository.save(Member.builder().name(NAME).memberType(MemberType.KAKAO).role(Role.USER).build());
        Product product = productRepository.save(Product.builder().title(TITLE).thumbnailImage(IMAGE_URL).category(category).member(member).build());

        // when
        Long offerId = offerService.createOffer(member.getId(), product.getId());
        Offer offer = offerService.findOfferByOfferId(offerId);

        // then
        assertEquals(OfferType.SENT, offer.getCondition());
    }

    @Test
    @Transactional
    public void 거래_요청_실패() throws Exception{
        // given
        Category category = categoryRepository.save(Category.builder().name("유모차").build());
        Member member = memberRepository.save(Member.builder().name(NAME).memberType(MemberType.KAKAO).role(Role.USER).build());
        Product product = productRepository.save(Product.builder().title(TITLE).thumbnailImage(IMAGE_URL).category(category).member(member).build());

        try{
            // when
            offerService.createOffer(member.getId(), 2L);

        }catch (Exception e){
            // then
            assertEquals("해당 거래 글은 존재하지 않습니다.", e.getMessage());
        }
    }

}
