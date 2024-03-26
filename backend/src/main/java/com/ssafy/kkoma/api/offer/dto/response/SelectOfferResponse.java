package com.ssafy.kkoma.api.offer.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.kkoma.api.member.dto.response.MemberProfileResponse;
import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.offer.constant.OfferType;
import com.ssafy.kkoma.domain.offer.entity.Offer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SelectOfferResponse {
	private Long offerId;
	private MemberProfileResponse buyerProfile; // 구매자 정보
	private Long dealId;
	private LocalDateTime dealTime; // 거래 시간

	public static SelectOfferResponse fromEntity(Offer offer, Deal deal) {
		return SelectOfferResponse.builder()
			.offerId(offer.getId())
			.buyerProfile(MemberProfileResponse.fromEntity(offer.getMember()))
			.dealId(deal.getId())
			.dealTime(deal.getSelectedTime())
			.build();
	}
}
