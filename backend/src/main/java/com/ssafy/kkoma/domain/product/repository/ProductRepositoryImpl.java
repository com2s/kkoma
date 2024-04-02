package com.ssafy.kkoma.domain.product.repository;

import java.util.List;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.kkoma.api.product.dto.request.SearchProductRequest;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.global.util.AreaCodeUtils;

import static com.ssafy.kkoma.domain.product.entity.QProduct.product;
import static com.ssafy.kkoma.domain.location.entity.QLocation.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Product> searchProduct(SearchProductRequest searchProductRequest, Pageable pageable) {
		JPAQuery<Product> query = queryFactory
			.selectFrom(product)
			.leftJoin(product.location, location)
			.where(product.deletedAt.isNull(),
				categoryIdEq(searchProductRequest.getCategoryId()),
				locationEq(searchProductRequest.getRegionCode()),
				statusEq(searchProductRequest.getStatus()),
				memberIdEq(searchProductRequest.getMemberId()),
				keywordContains(searchProductRequest.getKeyword())
			);

		for (Sort.Order o : pageable.getSort()) {
			PathBuilder pathBuilder = new PathBuilder(product.getType(), product.getMetadata());
			query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
				pathBuilder.get(o.getProperty())));
		}

		List<Product> contents = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return PageableExecutionUtils.getPage(contents, pageable, query::fetchCount);
	}

	private BooleanExpression categoryIdEq(Integer categoryId) {
		return categoryId == null ? null : product.category.id.eq(categoryId);
	}

	private BooleanExpression locationEq(Long regionCode) {
		return regionCode == null ? null : locationRange(regionCode);
	}

	private BooleanExpression locationRange(Long regionCode) {
		long nextRegionCode = regionCode + AreaCodeUtils.getDigitByCode(regionCode);
		return product.location.regionCode.goe(regionCode).and(product.location.regionCode.lt(nextRegionCode));
	}

	private BooleanExpression memberIdEq(Long memberId) {
		return memberId == null ? null : product.member.id.eq(memberId);
	}

	private BooleanExpression statusEq(ProductType status) {
		return status == null ? null : product.status.eq(status);
	}

	private BooleanExpression keywordContains(String keyword) {
		return keyword == null ? null : product.title.contains(keyword).or(product.description.contains(keyword));
	}

}
