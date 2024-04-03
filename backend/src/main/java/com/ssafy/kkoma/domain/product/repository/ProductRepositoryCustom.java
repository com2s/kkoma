package com.ssafy.kkoma.domain.product.repository;

import com.ssafy.kkoma.api.product.dto.hourly.ProductHourlyWished;
import com.ssafy.kkoma.api.product.dto.request.SearchProductRequest;
import com.ssafy.kkoma.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepositoryCustom {

	Page<Product> searchProduct(SearchProductRequest searchProductRequest, Pageable pageable);

	List<ProductHourlyWished> getHourlyMostWishedProducts(int limit, LocalDateTime now);

}
