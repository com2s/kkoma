package com.ssafy.kkoma.domain.product.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.kkoma.domain.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.domain.product.dto.response.ProductDetailResponse;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.kkoma.domain.product.dto.response.ProductSummary;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductImageService productImageService;
	private final CategoryService categoryService;

	public List<ProductSummary> getProducts(){
		List<Product> products = productRepository.findAll();

		return products.stream()
			.map(ProductSummary::fromEntity)
			.collect(Collectors.toList());
	}

	public ProductDetailResponse getProduct(Long productId) {
		List<String> productImages = productImageService.getProductImages(productId);

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_EXISTS));

		String categoryName = categoryService.getCategoryName(product.getCategory().getId());

		MemberSummaryResponse memberSummaryResponse = MemberSummaryResponse.fromEntity(product.getMember());

		return ProductDetailResponse.builder()
				.id(productId)
				.productImages(productImages)
				.title(product.getTitle())
				.description(product.getDescription())
				.categoryName(categoryName)
				.price(product.getPrice())
				.condition(product.getCondition())
				.dealPlace(product.getPlaceDetail())
				.elapsedMinutes(Duration.between(product.getCreatedAt(), LocalDateTime.now()).toMinutes())
				.memberSummary(memberSummaryResponse)
				.wishCount(product.getWishCount())
				.offerCount(product.getOfferCount())
				.viewCount(product.getViewCount())
				.build();
	}

}
