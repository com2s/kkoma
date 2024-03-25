package com.ssafy.kkoma.domain.product.repository;

import java.util.List;

import com.ssafy.kkoma.api.product.dto.request.SearchProductRequest;
import com.ssafy.kkoma.domain.product.entity.Product;

public interface ProductRepositoryCustom {

	List<Product> searchProduct(SearchProductRequest searchProductRequest);

}
