package com.ssafy.kkoma.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.kkoma.domain.product.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
	ProductImage findTop1ByProductId(Long productId);

}
