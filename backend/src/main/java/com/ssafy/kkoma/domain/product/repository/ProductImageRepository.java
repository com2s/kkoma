package com.ssafy.kkoma.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.kkoma.domain.product.dto.response.ProductThumbnail;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
	public ProductImage findTop1ByProductId(Long productId);

}
