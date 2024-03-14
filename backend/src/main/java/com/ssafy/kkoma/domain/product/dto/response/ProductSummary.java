package com.ssafy.kkoma.domain.product.dto.response;

import com.ssafy.kkoma.domain.product.constant.ProductType;

import com.ssafy.kkoma.domain.product.entity.Product;
import lombok.Builder;

import java.time.Duration;
import java.time.LocalDateTime;

@Builder
public class ProductSummary {

	private Long id;
	private String thumbnailImage;
	private String title;
	private String dealPlace;
	private int price;
	private ProductType condition;
	private Long elapsedMinutes;

	public static ProductSummary fromEntity(Product product){
		return ProductSummary.builder()
				.id(product.getId())
				.thumbnailImage(product.getThumbnailImage())
				.title(product.getThumbnailImage())
				.dealPlace(product.getPlaceDetail())
				.price(product.getPrice())
				.condition(product.getCondition())
				.elapsedMinutes(Duration.between(product.getCreatedAt(), LocalDateTime.now()).toMinutes())
				.build();
	}

}
