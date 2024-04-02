package com.ssafy.kkoma.domain.product.repository;

import com.ssafy.kkoma.api.product.dto.hourly.ProductHourlyWished;
import com.ssafy.kkoma.api.product.dto.request.SearchProductRequest;
import com.ssafy.kkoma.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ProductRepositoryCustom {

	Page<Product> searchProduct(SearchProductRequest searchProductRequest, Pageable pageable);

	Page<ProductHourlyWished> getHourlyMostWishedProducts(int limit, LocalDateTime now, Pageable pageable);

}
