package com.ssafy.kkoma.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.kkoma.api.product.dto.request.SearchProductRequest;
import com.ssafy.kkoma.domain.product.entity.Product;

public interface ProductRepositoryCustom {

	Page<Product> searchProduct(SearchProductRequest searchProductRequest, Pageable pageable);

}
