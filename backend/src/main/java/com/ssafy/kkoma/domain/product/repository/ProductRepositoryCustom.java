package com.ssafy.kkoma.domain.product.repository;

import com.ssafy.kkoma.api.product.dto.ProductHourlyViewed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.kkoma.api.product.dto.request.SearchProductRequest;
import com.ssafy.kkoma.domain.product.entity.Product;

import java.time.LocalDateTime;

public interface ProductRepositoryCustom {

	Page<Product> searchProduct(SearchProductRequest searchProductRequest, Pageable pageable);

	Page<ProductHourlyViewed> getMostViewedProductsPerHour(int limit, LocalDateTime now, Pageable pageable);

}
