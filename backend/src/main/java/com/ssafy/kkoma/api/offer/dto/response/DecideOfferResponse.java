package com.ssafy.kkoma.api.offer.dto.response;

import com.ssafy.kkoma.api.member.dto.response.MemberProfileResponse;
import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DecideOfferResponse {
	private Long offerId;
	private MemberProfileResponse buyerProfile; // 구매자 정보
	private Long dealId;
	private LocalDateTime dealTime; // 거래 시간
	private Long chatRoomId;

	public static DecideOfferResponse fromEntity(Offer offer, Deal deal, Long chatRoomId) {
		return DecideOfferResponse.builder()
			.offerId(offer.getId())
			.buyerProfile(MemberProfileResponse.fromEntity(offer.getMember()))
			.dealId(deal.getId())
			.dealTime(deal.getSelectedTime())
			.chatRoomId(chatRoomId)
			.build();
	}
}
