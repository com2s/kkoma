package com.ssafy.kkoma.domain.product.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.kkoma.domain.product.dto.response.ProductSummary;
import com.ssafy.kkoma.domain.product.dto.response.ProductThumbnail;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.ProductImage;
import com.ssafy.kkoma.domain.product.repository.ProductImageRepository;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductImageRepository productImageRepository;
	private final ProductRepository productRepository;

	public List<ProductSummary> getProducts(){
		List<Product> products = productRepository.findAll();

		return products.stream()
			.map(product -> {
				ProductImage productImage = productImageRepository.findTop1ByProductId(product.getId());
				return ProductSummary.builder()
					.id(product.getId())
					.title(product.getTitle())
					.price(product.getPrice())
					.condition(product.getCondition())
					.elapsedMinutes(Duration.between(product.getCreatedAt(), LocalDateTime.now()).toMinutes())
					.productThumbnail(ProductThumbnail.fromEntity(productImage))
					.build();
			})
			.collect(Collectors.toList());
	}

}
