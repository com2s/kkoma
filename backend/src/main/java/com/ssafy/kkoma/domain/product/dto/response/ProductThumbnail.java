package com.ssafy.kkoma.domain.product.dto.response;

import com.ssafy.kkoma.domain.product.entity.ProductImage;

import lombok.Builder;

@Builder
public class ProductThumbnail {

	private String thumbnailImage;

	public static ProductThumbnail fromEntity(ProductImage productImage){
		return ProductThumbnail.builder()
			.thumbnailImage(productImage.getFilename())
			.build();
	}

}
