package com.ssafy.kkoma.api.deal.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.kkoma.factory.CategoryFactory;
import com.ssafy.kkoma.factory.MemberFactory;
import com.ssafy.kkoma.factory.OfferFactory;
import com.ssafy.kkoma.factory.ProductFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.kkoma.api.deal.dto.request.DecideOfferRequest;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.offer.service.OfferService;
import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.deal.repository.DealRepository;
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
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;

@Slf4j
@SpringBootTest
class DealServiceTest {

	@Autowired
	MemberFactory memberFactory;
	@Autowired
	CategoryFactory categoryFactory;
	@Autowired
	OfferFactory offerFactory;
	@Autowired
	ProductFactory productFactory;

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

	@Test
	@Transactional
	public void 거래_생성() throws Exception {
	    // given
		Category category = categoryFactory.createCategory("목욕 용품");
		Member seller = memberFactory.createMember();
		Product product = productFactory.createProduct(seller, category, 20000);
		Member buyer = memberFactory.createMember();
		Offer offer = offerFactory.createOffer(product, buyer);
		DecideOfferRequest decideOfferRequest = DecideOfferRequest.builder().selectedTime(LocalDateTime.now()).build();

		// when
		Deal deal = dealService.createDeal(offer, decideOfferRequest);

	    // then
		assertEquals(deal.getProduct().getId(), product.getId());
	}

	@Test
	@Transactional
	void 거래_코드_요청() throws Exception {
		// given
		Category category = categoryFactory.createCategory("목욕 용품");
		Member seller = memberFactory.createMember();
		Product product = productFactory.createProduct(seller, category, 20000);
		Member buyer = memberFactory.createMember();
		Offer offer = offerFactory.createOffer(product, buyer);
		DecideOfferRequest decideOfferRequest = DecideOfferRequest.builder().selectedTime(LocalDateTime.now()).build();

		offerService.acceptOffer(offer.getId(), decideOfferRequest);
		Deal deal = dealRepository.findByProduct(product);

		// when & then

		// 1) 구매자가 거래 코드 요청
		String buyerReqCode = dealService.getCode(deal.getId(), buyer.getId());
		assertEquals(deal.getCode(), buyerReqCode);

		// 2) 비구매자가 거래 코드 요청
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			dealService.getCode(deal.getId(), seller.getId());
		});
		assertEquals(exception.getErrorCode(), ErrorCode.INVALID_BUYER);
	}

	@Test
	@Transactional
	void 거래_완료_처리() throws Exception {
		// given
		Category category = categoryFactory.createCategory("목욕 용품");
		Member seller = memberFactory.createMember();
		Product product = productFactory.createProduct(seller, category, 20000);
		Member buyer = memberFactory.createMember();
		Offer offer = offerFactory.createOffer(product, buyer);
		DecideOfferRequest decideOfferRequest = DecideOfferRequest.builder().selectedTime(LocalDateTime.now()).build();

		offerService.acceptOffer(offer.getId(), decideOfferRequest);
		Deal deal = dealRepository.findByProduct(product);

		String codeSellerGotFromBuyer = dealService.getCode(deal.getId(), buyer.getId()); // 판매자가 구매자의 QR코드를 읽어 코드를 얻었다

		// when & then
		assertNotNull(dealService.finishDeal(deal.getId(), seller.getId(), codeSellerGotFromBuyer));
	}

	@Test
	@Transactional
	void 거래_예정_거래_조회() throws Exception {
		// given
		LocalDateTime now = LocalDateTime.now();
		Category category = categoryFactory.createCategory("목욕 용품");
		Member seller = memberFactory.createMember();

		List<LocalDateTime> dealTimeList = List.of(new LocalDateTime[]{
			now.plusHours(1),
			now.plusHours(1).plusMinutes(1).withSecond(0).withNano(0),
			now.plusHours(1).withSecond(59)
		});

		for (LocalDateTime dealTime : dealTimeList) {
			Product product = productFactory.createProduct(seller, category, 20000);
			Member buyer = memberFactory.createMember();
			Offer offer = offerFactory.createOffer(product, buyer);
			DecideOfferRequest decideOfferRequest = DecideOfferRequest.builder()
					.selectedTime(dealTime)
					.build();
			offerService.acceptOffer(offer.getId(), decideOfferRequest);
//			log.info("dealTime은 : {}", dealTime);
		}

		Pageable pageable = PageRequest.of(0,10);

		// when
		List<Deal> scheduledDealList = dealService.findScheduledDeal(now, pageable);

		// then
		assertEquals(2, scheduledDealList.size());
	}

}
