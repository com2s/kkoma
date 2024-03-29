package com.ssafy.kkoma.api.product.dto.response;

import java.util.List;

import com.ssafy.kkoma.api.product.dto.ProductSummary;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyWishProductResponse {

	private List<ProductSummary> content;
	private int size;
	private int page;
	private int numberOfElements;
	private long totalElements;
	private int totalPages;
	private boolean first;
	private boolean last;
	private boolean empty;

}
