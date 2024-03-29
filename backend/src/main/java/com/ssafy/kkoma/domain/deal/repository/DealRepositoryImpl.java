package com.ssafy.kkoma.domain.deal.repository;

import static com.ssafy.kkoma.domain.deal.entity.QDeal.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.kkoma.domain.deal.entity.Deal;

import com.ssafy.kkoma.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@Slf4j
@RequiredArgsConstructor
public class DealRepositoryImpl implements DealRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Deal> findScheduledDeal(LocalDateTime now, Pageable pageable) {
		JPAQuery<Deal> query = queryFactory
			.selectFrom(deal)
			.where(deal.cancelledAt.isNull(),
				deal.isCompleted.isFalse(),
				timeLeft(now, deal.selectedTime)
			);

		List<Deal> contents = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return PageableExecutionUtils.getPage(contents, pageable, query::fetchCount);
	}

	private BooleanExpression timeLeft(LocalDateTime now, DateTimePath<LocalDateTime> selectedTime) {
		LocalDateTime startTime = now.plusHours(1).withSecond(0).withNano(0);
		LocalDateTime endTime = startTime.plusSeconds(59).withNano(999999998);
		log.info("거래 시간이 {} ~ {} 사이인 경우만", startTime, endTime);
		return selectedTime.between(startTime, endTime);
	}
}
