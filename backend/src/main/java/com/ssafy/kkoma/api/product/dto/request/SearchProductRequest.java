package com.ssafy.kkoma.api.product.dto.request;

import com.ssafy.kkoma.domain.product.constant.ProductType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchProductRequest {

	private Long regionCode;
	private Integer categoryId;
	private Long memberId;
	private String keyword;
	private ProductType status;

}
