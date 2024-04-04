package com.ssafy.kkoma.domain.point.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ssafy.kkoma.domain.point.entity.Point;

public enum PointChangeType {

	// 충전, 지불, 수익, 반환, 인출
	CHARGE, PAY, PROFIT, REFUND, WITHDRAW
	;

	@JsonCreator
	public static PointChangeType from(String str) {
		return PointChangeType.valueOf(str.toUpperCase());
	}

//	USE, GET
}
