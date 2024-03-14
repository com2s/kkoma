package com.ssafy.kkoma.domain.product.dto.response;

import com.ssafy.kkoma.domain.product.constant.ProductType;

import lombok.Builder;

@Builder
public class ProductSummary {

	private Long id;
	private ProductThumbnail productThumbnail;
	private String title;
	private int price;
	private ProductType condition;
	private Long elapsedMinutes;

}
