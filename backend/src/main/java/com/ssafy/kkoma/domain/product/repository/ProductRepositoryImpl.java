package com.ssafy.kkoma.domain.product.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.kkoma.api.product.dto.ProductHourlyViewed;
import com.ssafy.kkoma.api.product.dto.request.SearchProductRequest;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.global.util.AreaCodeUtils;

import static com.ssafy.kkoma.domain.product.entity.QProduct.product;
import static com.ssafy.kkoma.domain.location.entity.QLocation.location;
import static com.ssafy.kkoma.domain.product.entity.QWishList.wishList;

import com.ssafy.kkoma.domain.product.entity.QWishList;
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

	// 지난 1시간 동안 가장 많은 조회수를 받은 상품글(판매중이든 거래완료든) 목록
	/*
	    select p.*, count(w.id) as wish_count_on_hour
		from wish_list as w
				 left join product as p on p.id = w.product_id
		where w.is_valid = 1 and w.created_at between '2024-04-02 01:00:00' AND '2024-04-02 13:10:00'
		group by w.product_id
		order by wish_count_on_hour;
	 */
	@Override
	public Page<ProductHourlyViewed> getMostViewedProductsPerHour(int limit, LocalDateTime now, Pageable pageable) {
		LocalDateTime nowOnClock = now.withMinute(0).withSecond(0).withNano(0);
		LocalDateTime hourAgoOnClock = nowOnClock.minusHours(1);

		NumberPath<Long> aliasWishCount = Expressions.numberPath(Long.class, "hourlyViewCount");

		JPAQuery<ProductHourlyViewed> query = queryFactory
				.select(Projections.constructor(
								ProductHourlyViewed.class,
								wishList.product.id.as("id"),
								product.thumbnailImage.as("thumbnailImage"),
								product.title.as("title"),
								product.status.as("status"),
								product.price.as("price"),
								product.wishCount.as("wishCount"),
								product.viewCount.as("viewCount"),
								product.offerCount.as("offerCount"),
								wishList.id.count().as(aliasWishCount),
								product.createdAt.as("createdAt")
						)
				)
				.from(wishList)
				.leftJoin(product).on(wishList.product.id.eq(product.id))
				.where(wishList.isValid.eq(true)
						.and(wishList.createdAt.between(hourAgoOnClock, nowOnClock))
				)
				.groupBy(wishList.product.id)
				.orderBy(aliasWishCount.desc())
				.limit(limit);

		List<ProductHourlyViewed> contents = query.fetch();

		return PageableExecutionUtils.getPage(contents, pageable, query::fetchCount);
	}

}
