package com.ssafy.kkoma.api.offer.dto.response;

import java.util.List;

import com.ssafy.kkoma.api.member.dto.response.MemberProfileResponse;
import com.ssafy.kkoma.api.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.offer.constant.OfferType;
import com.ssafy.kkoma.domain.offer.entity.Offer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OfferResponse {

	private Long id;
	private MemberProfileResponse memberProfile;
	private OfferType status;
	private List<OfferTimeResponse> offerTimes;

	public static OfferResponse fromEntity(Offer offer) {
		return OfferResponse.builder()
			.id(offer.getId())
			.memberProfile(MemberProfileResponse.fromEntity(offer.getMember()))
			.status(offer.getStatus())
			.offerTimes(offer.getOfferDetails().stream().map(OfferTimeResponse::fromEntity).toList())
			.build();
	}
}
