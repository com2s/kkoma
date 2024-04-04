package com.ssafy.kkoma.api.product.dto;

import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.domain.area.repository.AreaRepository;
import com.ssafy.kkoma.domain.product.constant.ProductType;

import com.ssafy.kkoma.domain.product.entity.Product;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDateTime;

@SuperBuilder // 부모 객체를 상속 받는 자식 객체를 만들 때, 부모 객체의 필드값도 지정 가능
@Getter
@ToString
@Data
public class ProductSummary {

	protected Long id;
	protected String thumbnailImage;
	protected String title;

	@Setter
	protected String dealPlace;

	protected ProductType status;
	protected int price;

	@Setter
	protected Long elapsedMinutes;

	protected Long wishCount;
	protected Long viewCount;
	protected Long offerCount;

	public ProductSummary(
		Long id, String thumbnailImage, String title, ProductType status,
		int price, Long wishCount, Long viewCount, Long offerCount
	) {
		this.id = id;
		this.thumbnailImage = thumbnailImage;
		this.title = title;
		this.status = status;
		this.price = price;
		this.wishCount = wishCount;
		this.viewCount = viewCount;
		this.offerCount = offerCount;
	}

	public static ProductSummary fromEntity(Product product){
		LocalDateTime createdAt = product.getCreatedAt();
		Duration elapsedDuration = (createdAt != null) ? Duration.between(createdAt, LocalDateTime.now()) : null;

		return ProductSummary.builder()
				.id(product.getId())
				.thumbnailImage(product.getThumbnailImage())
				.title(product.getTitle())
				.status(product.getStatus())
				.price(product.getPrice())
				.elapsedMinutes((elapsedDuration != null) ? elapsedDuration.toMinutes() : null)
				.wishCount(product.getWishCount())
				.viewCount(product.getViewCount())
				.offerCount(product.getOfferCount())
				.build();
	}

	public static ProductSummary fromEntity(Product product, Area area){
		LocalDateTime createdAt = product.getCreatedAt();
		Duration elapsedDuration = (createdAt != null) ? Duration.between(createdAt, LocalDateTime.now()) : null;

		return ProductSummary.builder()
				.id(product.getId())
				.thumbnailImage(product.getThumbnailImage())
				.title(product.getTitle())
				.status(product.getStatus())
				.price(product.getPrice())
				.elapsedMinutes((elapsedDuration != null) ? elapsedDuration.toMinutes() : null)
				.wishCount(product.getWishCount())
				.viewCount(product.getViewCount())
				.offerCount(product.getOfferCount())
				.dealPlace(area.getFullArea())
				.build();
	}

}
