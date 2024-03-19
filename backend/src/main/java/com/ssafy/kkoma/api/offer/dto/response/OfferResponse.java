package com.ssafy.kkoma.api.offer.dto.response;

import java.util.List;

import com.ssafy.kkoma.api.member.dto.response.MemberProfileResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OfferResponse {

	private Long id;
	private MemberProfileResponse memberProfile;
	private List<OfferTimeResponse> offerTimes;

}
