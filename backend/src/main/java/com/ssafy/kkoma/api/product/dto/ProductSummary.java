package com.ssafy.kkoma.api.product.dto;

import com.ssafy.kkoma.domain.product.constant.ProductType;

import com.ssafy.kkoma.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class ProductSummary {

	private Long id;
	private String thumbnailImage;
	private String title;
	private String dealPlace;
	private int price;
	private ProductType status;
	private Long elapsedMinutes;

	public static ProductSummary fromEntity(Product product){
		LocalDateTime createdAt = product.getCreatedAt();
		Duration elapsedDuration = (createdAt != null) ? Duration.between(createdAt, LocalDateTime.now()) : null;

		return ProductSummary.builder()
				.id(product.getId())
				.thumbnailImage(product.getThumbnailImage())
				.title(product.getTitle())
				.dealPlace(product.getPlaceDetail())
				.price(product.getPrice())
				.status(product.getStatus())
				.elapsedMinutes((elapsedDuration != null) ? elapsedDuration.toMinutes() : null)
				.build();
	}

}
