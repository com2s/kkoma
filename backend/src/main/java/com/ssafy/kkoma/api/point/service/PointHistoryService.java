package com.ssafy.kkoma.api.point.service;

import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.point.constant.PointChangeType;
import com.ssafy.kkoma.domain.point.entity.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ssafy.kkoma.domain.point.entity.PointHistory;
import com.ssafy.kkoma.domain.point.repository.PointHistoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PointHistoryService {

	private final PointHistoryRepository pointHistoryRepository;

	public void changePoint(Member member, PointChangeType pointChangeType, int amount) {
		Point point = member.getPoint();

		if (pointChangeType == PointChangeType.CHARGE ||pointChangeType == PointChangeType.PROFIT) {
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

	public void createPointHistory(PointHistory pointHistory) {
		pointHistoryRepository.save(pointHistory);
	}

}
