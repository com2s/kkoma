package com.ssafy.kkoma.api.product.dto;

import java.time.Duration;
import java.time.LocalDateTime;

import com.ssafy.kkoma.domain.product.constant.MyProductType;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductInfoResponse {

	private Long id;
	private String thumbnailImage;
	private String title;
	private String dealPlace;
	private ProductType status;
	private Long elapsedMinutes;
	private Long wishCount;
	private Long viewCount;
	private Long offerCount;
	private MyProductType type;

	public static ProductInfoResponse fromEntity(Product product, MyProductType type){
		LocalDateTime createdAt = product.getCreatedAt();
		Duration elapsedDuration = (createdAt != null) ? Duration.between(createdAt, LocalDateTime.now()) : null;

		return ProductInfoResponse.builder()
			.id(product.getId())
			.thumbnailImage(product.getThumbnailImage())
			.title(product.getTitle())
			.dealPlace(product.getPlaceDetail())
			.status(product.getStatus())
			.elapsedMinutes((elapsedDuration != null) ? elapsedDuration.toMinutes() : null)
			.wishCount(product.getWishCount())
			.viewCount(product.getViewCount())
			.offerCount(product.getOfferCount())
			.type(type)
			.build();
	}

}
