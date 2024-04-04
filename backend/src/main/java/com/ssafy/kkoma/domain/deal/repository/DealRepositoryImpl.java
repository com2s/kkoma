package com.ssafy.kkoma.domain.deal.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.kkoma.domain.deal.entity.Deal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.kkoma.domain.deal.entity.QDeal.deal;

@Slf4j
@RequiredArgsConstructor
public class DealRepositoryImpl implements DealRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Deal> findScheduledDealAfterLastRun(LocalDateTime lastRun, LocalDateTime curRun, Pageable pageable) {
		JPAQuery<Deal> query = queryFactory
			.selectFrom(deal)
			.where(deal.cancelledAt.isNull(),
				deal.isCompleted.isFalse(),
				createdLaterThanLastRun(lastRun, deal.createdAt),
				timeLeft(curRun, deal.selectedTime)
			);

		List<Deal> contents = query
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return PageableExecutionUtils.getPage(contents, pageable, query::fetchCount);
	}

	private BooleanExpression createdLaterThanLastRun(LocalDateTime lastRun, DateTimePath<LocalDateTime> createdTime) {
		log.info("지난 번 실행된 {} 이후에 생성된 deal", lastRun);
		return createdTime.after(lastRun);
	}

	private BooleanExpression timeLeft(LocalDateTime curRun, DateTimePath<LocalDateTime> selectedTime) {
		LocalDateTime posRemindTime = curRun.plusMinutes(59).withSecond(59).withNano(999999999);
		log.info("지금으로부터 1시간 이상, 즉 {} 이후가 거래 시간이어야 해", posRemindTime);
		return selectedTime.after(posRemindTime);
	}
}
