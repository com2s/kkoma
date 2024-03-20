package com.ssafy.kkoma.api.deal.service;

import org.springframework.stereotype.Service;

import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.deal.repository.DealRepository;
import com.ssafy.kkoma.domain.deal.request.DealTimeRequest;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.product.entity.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DealService {

	private final DealRepository dealRepository;

	public Deal createDeal(Offer offer, DealTimeRequest dealTimeRequest){
		return dealRepository.save(Deal.builder()
			.member(offer.getMember())
			.product(offer.getProduct())
			.selectedTime(dealTimeRequest.getSelectedTime())
			.build());
	}

}
