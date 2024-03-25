package com.ssafy.kkoma.api.deal.service;

import org.springframework.stereotype.Service;

import com.ssafy.kkoma.api.deal.dto.request.DealTimeRequest;
import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.deal.repository.DealRepository;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.product.entity.Product;

import org.springframework.transaction.annotation.Transactional;

import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DealService {

	private final DealRepository dealRepository;

	public Deal findDealByDealId(Long dealId){
		return dealRepository.findById(dealId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.DEAL_NOT_EXISTS));
	}

	public Deal createDeal(Offer offer, DealTimeRequest dealTimeRequest){
		return dealRepository.save(Deal.builder()
			.member(offer.getMember())
			.product(offer.getProduct())
			.build());
	}

	// 상품 상태가 '거래중'이고 구매자가 맞는지 확인
	public String getCode(Long dealId, Long memberId){
		Deal deal = findDealByDealId(dealId);

		Product product = deal.getProduct();
		if (!product.getStatus().equals(ProductType.PROGRESS)) { // 거래 중이 아닌 상품
			throw new BusinessException(ErrorCode.DEAL_INVALID_STATUS);
		}

		Member buyer = deal.getMember();
		if (!buyer.getId().equals(memberId)) { // 구매자가 아님
			throw new BusinessException(ErrorCode.INVALID_BUYER);
		}

		return deal.getCode();
	}

	public Deal finishDeal(Long dealId, Long memberId, String code) {
		Deal deal = findDealByDealId(dealId);

		if (!deal.getCode().equals(code)) { // 무효한 코드
			throw new BusinessException(ErrorCode.INVALID_CODE);
		}

		Product product = deal.getProduct();
		Member seller = product.getMember();

		if (!product.getStatus().equals(ProductType.PROGRESS)) { // 거래 중이 아닌 상품
			throw new BusinessException(ErrorCode.DEAL_INVALID_STATUS);
		}

		if (!seller.getId().equals(memberId)) { // 판매자가 아님
			throw new BusinessException(ErrorCode.INVALID_SELLER);
		}

		// 판매자에게 포인트 넣어주기
		seller.getPoint().addBalance(product.getPrice());

		// 상품 status 변경
		product.updateStatus(ProductType.SOLD);

		// 거래 status 변경
		deal.updateIsCompleted(Boolean.TRUE);

		return deal;
	}

}
