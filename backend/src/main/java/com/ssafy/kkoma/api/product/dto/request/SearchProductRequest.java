package com.ssafy.kkoma.api.product.dto.request;

import com.ssafy.kkoma.domain.product.constant.ProductType;

import lombok.Getter;

@Getter
public class SearchProductRequest {
	private Integer size;
	private Integer page;
	private Integer regionCode;
	private Integer categoryId;
	private String keyword;
	private ProductType status;
	private Integer ageLoe;
	private Integer ageGoe;
}
