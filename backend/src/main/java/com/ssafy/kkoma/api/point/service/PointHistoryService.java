package com.ssafy.kkoma.api.point.service;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.point.dto.PointHistorySummary;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.point.constant.PointChangeType;
import com.ssafy.kkoma.domain.point.entity.Point;
import com.ssafy.kkoma.domain.point.entity.PointHistory;
import com.ssafy.kkoma.domain.point.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PointHistoryService {

	private final MemberService memberService;
	private final PointHistoryRepository pointHistoryRepository;

	public void changePoint(Member member, PointChangeType pointChangeType, int amount) {
		Point point = member.getPoint();

		if (pointChangeType == PointChangeType.CHARGE || pointChangeType == PointChangeType.PROFIT) {
			point.addBalance(amount);
		} else {
			point.subBalance(amount);
		}

		PointHistory pointHistory = PointHistory.builder()
				.point(point)
				.amount(amount)
				.pointChangeType(pointChangeType)
				.balanceAfterChange(point.getBalance())
				.build();
		pointHistoryRepository.save(pointHistory);
	}

	public BasePageResponse<PointHistory, PointHistorySummary> getPointHistory(Long memberId, Pageable pageable) {
		Member member = memberService.findMemberByMemberId(memberId);
		Page<PointHistory> page = pointHistoryRepository.findByPoint(member.getPoint(), pageable);

		List<PointHistorySummary> content = page.getContent().stream()
			.map(PointHistorySummary::fromEntity)
			.toList();

		return new BasePageResponse<>(content, page);
	}

	public void createPointHistory(PointHistory pointHistory) {
		pointHistoryRepository.save(pointHistory);
	}

}
