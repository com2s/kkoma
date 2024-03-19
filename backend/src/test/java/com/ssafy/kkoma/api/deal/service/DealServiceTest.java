package com.ssafy.kkoma.api.deal.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.deal.request.DealTimeRequest;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.offer.repository.OfferRepository;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.CategoryRepository;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;

@SpringBootTest
class DealServiceTest {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	OfferRepository offerRepository;

	@Autowired
	DealService dealService;

	private static final String TITLE = "TITLE";
	private static final String IMAGE_URL = "IMAGE_URL";
	private static final String NAME = "NAME";

	@Test
	@Transactional
	public void 거래_요청_수락_시_거래_생성() throws Exception{
	    // given
		Category category = categoryRepository.save(Category.builder().name("유모차").build());
		Member member = memberRepository.save(Member.builder().name(NAME).memberType(MemberType.KAKAO).role(Role.USER).build());
		Product product = productRepository.save(Product.builder().title(TITLE).thumbnailImage(IMAGE_URL).category(category).member(member).build());
		Offer offer = offerRepository.save(Offer.builder().product(product).member(member).build());

		DealTimeRequest dealTimeRequest = DealTimeRequest.builder().selectedTime(LocalDateTime.now()).build();

		// when
		Deal deal = dealService.createDeal(offer, dealTimeRequest);

	    // then
		assertEquals(deal.getProduct().getId(), product.getId());
	}

}
