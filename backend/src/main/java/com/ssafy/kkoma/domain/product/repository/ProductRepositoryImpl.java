package com.ssafy.kkoma.domain.product.repository;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.kkoma.api.product.dto.request.SearchProductRequest;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;
import static com.ssafy.kkoma.domain.product.entity.QProduct.product;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Product> searchProduct(SearchProductRequest searchProductRequest) {
		return queryFactory
			.selectFrom(product)
			.where(product.deletedAt.isNull(),
				categoryIdEq(searchProductRequest.getCategoryId()))
			.fetch();
	}

	private BooleanExpression categoryIdEq(Integer categoryId) {
		return categoryId == null ? null : product.category.id.eq(categoryId);
	}

	// TODO: location filter expression

	private BooleanExpression statusEq(ProductType status) {
		return status == null ? null : product.status.eq(status);
	}

	// TODO: keyword filter expression
	// private BooleanExpression

}
