package com.ssafy.kkoma.api.location.dto.request;

import com.ssafy.kkoma.domain.location.entity.Location;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateLocationRequest {

	private Long regionCode;
	private Double x;
	private Double y;
	private String placeDetail;

	public Location toEntity() {
		return Location.builder()
			.regionCode(this.regionCode)
			.x(this.x)
			.y(this.y)
			.placeDetail(this.placeDetail)
			.build();
	}
}
