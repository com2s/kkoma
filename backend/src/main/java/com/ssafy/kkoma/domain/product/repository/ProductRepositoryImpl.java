package com.ssafy.kkoma.domain.product.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.kkoma.api.product.dto.hourly.ProductHourlyWished;
import com.ssafy.kkoma.api.product.dto.hourly.QProductHourlyWished;
import com.ssafy.kkoma.api.product.dto.request.SearchProductRequest;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.global.util.AreaCodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.kkoma.domain.location.entity.QLocation.location;
import static com.ssafy.kkoma.domain.product.entity.QProduct.product;
import static com.ssafy.kkoma.domain.product.entity.QViewHistory.viewHistory;
import static com.ssafy.kkoma.domain.product.entity.QWishList.wishList;

@Slf4j
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
	public List<ProductHourlyWished> getHourlyMostWishedProducts(int limit, LocalDateTime now) {
		LocalDateTime endTime = now.withMinute(0).withSecond(0).withNano(0);
		LocalDateTime startTime = endTime.minusHours(1);
		endTime = endTime.minusNanos(5000);

		log.info("계산에 고려되는 createdAt 시간은 {} ~ {}", startTime, endTime);

		NumberPath<Long> aliasWishCount = Expressions.numberPath(Long.class, "hourlyWishCount");

		JPAQuery<ProductHourlyWished> query = queryFactory
				.select(
						new QProductHourlyWished(
								product.id,
								product.thumbnailImage,
								product.title,
								product.status,
								product.price,
								product.wishCount,
								product.viewCount,
								product.offerCount,
								wishList.id.count().as(aliasWishCount),
								product.location.regionCode,
								product.createdAt
						)
				)
				.from(wishList)
				.leftJoin(product).on(wishList.product.id.eq(product.id))
				.where(wishList.isValid.eq(true)
						.and(wishList.createdAt.between(startTime, endTime))
				)
				.groupBy(wishList.product.id)
				.orderBy(aliasWishCount.desc())
				.limit(limit);

		return query.fetch();
	}

	@Override
	public List<ProductHourlyWished> getHourlyMostViewedProducts(int limit, LocalDateTime now) {
		LocalDateTime endTime = now.withMinute(0).withSecond(0).withNano(0);
		LocalDateTime startTime = endTime.minusHours(1);
		endTime = endTime.minusNanos(5000);

		log.info("계산에 고려되는 createdAt 시간은 {} ~ {}", startTime, endTime);

		NumberPath<Long> aliasViewCount = Expressions.numberPath(Long.class, "hourlyViewCount");

		JPAQuery<ProductHourlyWished> query = queryFactory
				.select(
						new QProductHourlyWished(
								product.id,
								product.thumbnailImage,
								product.title,
								product.status,
								product.price,
								product.wishCount,
								product.viewCount,
								product.offerCount,
								viewHistory.id.count().as(aliasViewCount),
								product.location.regionCode,
								product.createdAt
						)
				)
				.from(viewHistory)
				.leftJoin(product).on(viewHistory.product.id.eq(product.id))
				.where(viewHistory.viewedAt.between(startTime, endTime))
				.groupBy(viewHistory.product.id)
				.orderBy(aliasViewCount.desc())
				.limit(limit);

		return query.fetch();
	}

}