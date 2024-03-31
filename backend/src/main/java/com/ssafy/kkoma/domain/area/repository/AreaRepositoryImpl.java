package com.ssafy.kkoma.domain.area.repository;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.kkoma.domain.area.entity.Area;
import static com.ssafy.kkoma.domain.area.entity.QArea.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AreaRepositoryImpl implements AreaRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Area> getArea(int level, Long code, Long digit, Long nextDigit) {
		return queryFactory
			.selectFrom(area)
			.where(
				areaRange(code, digit),
				areaLevel(nextDigit),
				codeEq(code)
			)
			.fetch();
	}

	private BooleanExpression areaLevel(Long digit) {
		return digit == null || digit == 0 ? null : area.id.mod(digit).eq(0L);
	}

	private BooleanExpression areaRange(Long code, Long digit) {
		return code == null ? null : areaGoe(code).and(areaLt(code, digit));
	}

	private BooleanExpression areaGoe(long code) {
		return area.id.goe(code);
	}

	private BooleanExpression areaLt(long code, long digit) {
		return area.id.lt(code + digit);
	}

	private BooleanExpression codeEq(Long code) {
		return code == null ? null : area.id.eq(code).not();
	}

}
