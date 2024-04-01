package com.ssafy.kkoma.api.deal.service;

import com.ssafy.kkoma.api.deal.dto.request.DecideOfferRequest;
import com.ssafy.kkoma.api.notification.constant.NotiDetailBuilder;
import com.ssafy.kkoma.api.notification.service.NotificationService;
import com.ssafy.kkoma.api.point.service.PointHistoryService;
import com.ssafy.kkoma.api.product.service.CategoryPreferenceService;
import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.deal.repository.DealRepository;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.point.constant.PointChangeType;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DealService {

	private final DealRepository dealRepository;
	private final NotificationService notificationService;
	private final PointHistoryService pointHistoryService;
	private final CategoryPreferenceService categoryPreferenceService;

	public Deal findDealByDealId(Long dealId){
		return dealRepository.findById(dealId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.DEAL_NOT_EXISTS));
	}

	public Deal findDealByProductId(Long productId) {
		return dealRepository.findDealByProductId(productId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.DEAL_NOT_EXISTS));
	}

	public Deal createDeal(Offer offer, DecideOfferRequest decideOfferRequest){
		return dealRepository.save(Deal.builder()
			.member(offer.getMember())
			.product(offer.getProduct())
			.selectedTime(decideOfferRequest.getSelectedTime())
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

		product.updateStatus(ProductType.SOLD); // 상품 status 변경
		deal.updateIsCompleted(Boolean.TRUE); // 거래 status 변경

		pointHistoryService.changePoint(seller, PointChangeType.PROFIT, product.getPrice());
		notificationService.createNotification(seller,
			NotiDetailBuilder.getInstance().receivePayment(
				product.getTitle(), product.getPrice(), seller.getPoint().getBalance()
			)
		);

		// todo-siyoon set category preference (거래 완료에 구매자 정보가 없어서 구현 못하는 중)
		// categoryPreferenceService.createCategoryPreference(memberId, catego.getId());

		return deal;
	}

	public List<Deal> findScheduledDealAfterLastRun(LocalDateTime lastRun, LocalDateTime curRun, Pageable pageable) {
		Page<Deal> pageList = dealRepository.findScheduledDealAfterLastRun(lastRun, curRun, pageable);
		return pageList.getContent();
	}

}
