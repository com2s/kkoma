package com.ssafy.kkoma.api.product.dto.response;

import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ChatProductResponse {

	private Long id;
	private String thumbnailImage;
	private String title;
	private int price;
	private ProductType status;
	private Long sellerId;

	@Setter
	private Long buyerId;

	public static ChatProductResponse fromEntity(Product product) {
		return ChatProductResponse.builder()
			.id(product.getId())
			.thumbnailImage(product.getThumbnailImage())
			.title(product.getTitle())
			.price(product.getPrice())
			.status(product.getStatus())
			.sellerId(product.getMember().getId())
			.build();
	}

}
