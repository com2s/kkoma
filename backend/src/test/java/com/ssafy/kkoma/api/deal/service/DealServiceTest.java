package com.ssafy.kkoma.api.deal.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.offer.service.OfferService;
import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.deal.repository.DealRepository;
import com.ssafy.kkoma.domain.deal.request.DealTimeRequest;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.offer.repository.OfferRepository;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.CategoryRepository;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;

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
	DealRepository dealRepository;

	@Autowired
	MemberService memberService;

	@Autowired
	OfferService offerService;

	@Autowired
	DealService dealService;

	private static final String TITLE = "TITLE";
	private static final String IMAGE_URL = "IMAGE_URL";
	private static final String NAME = "NAME";
	private static final String NAME_BUYER = "BUYER";
	private static final String NAME_SELLER = "SELLER";

	@Test
	@Transactional
	public void 거래_생성() throws Exception {
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

	@Test
	@Transactional
	void 구매자가_거래_코드_요청() throws Exception {
		// given
		Category category = categoryRepository.save(Category.builder().name("유모차").build());
		Member seller = memberRepository.save(Member.builder().name(NAME_SELLER).memberType(MemberType.KAKAO).role(Role.USER).build());
		Member buyer = memberRepository.save(Member.builder().name(NAME_BUYER).memberType(MemberType.KAKAO).role(Role.USER).build());
		Product product = productRepository.save(Product.builder().title(TITLE).thumbnailImage(IMAGE_URL).category(category).member(seller).build());

		Offer offer = offerRepository.save(Offer.builder().product(product).member(buyer).build());
		DealTimeRequest dealTimeRequest = DealTimeRequest.builder().selectedTime(LocalDateTime.now()).build();

		// when
		offerService.acceptOffer(offer.getId(), dealTimeRequest);
		Deal deal = dealRepository.findByProduct(product);
		String buyerReqCode = dealService.getCode(deal.getId(), buyer.getId());

		// then
		assertEquals(deal.getCode(), buyerReqCode);
	}

	@Test
	@Transactional
	void 비구매자가_거래_코드_요청() throws Exception {
		// given
		Category category = categoryRepository.save(Category.builder().name("유모차").build());
		Member seller = memberRepository.save(Member.builder().name(NAME_SELLER).memberType(MemberType.KAKAO).role(Role.USER).build());
		Member buyer = memberRepository.save(Member.builder().name(NAME_BUYER).memberType(MemberType.KAKAO).role(Role.USER).build());
		Product product = productRepository.save(Product.builder().title(TITLE).thumbnailImage(IMAGE_URL).category(category).member(seller).build());

		Offer offer = offerRepository.save(Offer.builder().product(product).member(buyer).build());
		DealTimeRequest dealTimeRequest = DealTimeRequest.builder().selectedTime(LocalDateTime.now()).build();

		// when
		offerService.acceptOffer(offer.getId(), dealTimeRequest);
		Deal deal = dealRepository.findByProduct(product);

		// then
		BusinessException exception = (BusinessException) assertThrows(BusinessException.class, () -> {
			dealService.getCode(deal.getId(), seller.getId());
		});
		assertEquals(exception.getErrorCode(), ErrorCode.INVALID_BUYER);
	}

	@Test
	@Transactional
	void 거래_완료_처리() throws Exception {
		// given
		Category category = categoryRepository.save(Category.builder().name("유모차").build());
		Member seller = memberService.registerMember(
			Member.builder().name(NAME_SELLER).memberType(MemberType.KAKAO)
				.email(NAME_SELLER).role(Role.USER).build()
		);
		Member buyer = memberService.registerMember(
			Member.builder().name(NAME_BUYER).memberType(MemberType.KAKAO)
				.email(NAME_BUYER).role(Role.USER).build()
		);
		Product product = productRepository.save(Product.builder().title(TITLE).thumbnailImage(IMAGE_URL).category(category).member(seller).build());

		Offer offer = offerRepository.save(Offer.builder().product(product).member(buyer).build());
		DealTimeRequest dealTimeRequest = DealTimeRequest.builder().selectedTime(LocalDateTime.now()).build();

		// when
		offerService.acceptOffer(offer.getId(), dealTimeRequest);
		Deal deal = dealRepository.findByProduct(product);
		String codeSellerGotFromBuyer = dealService.getCode(deal.getId(), buyer.getId()); // 판매자가 구매자의 QR코드를 읽어 코드를 얻었다

		// then
		assertNotNull(dealService.finishDeal(deal.getId(), seller.getId(), codeSellerGotFromBuyer));
	}
}
