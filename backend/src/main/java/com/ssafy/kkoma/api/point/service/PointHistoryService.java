package com.ssafy.kkoma.api.point.service;

import org.springframework.stereotype.Service;

import com.ssafy.kkoma.domain.point.constant.PointChangeType;
import com.ssafy.kkoma.domain.point.entity.Point;
import com.ssafy.kkoma.domain.point.entity.PointHistory;
import com.ssafy.kkoma.domain.point.repository.PointHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointHistoryService {

	private final PointHistoryRepository pointHistoryRepository;

	public void createPointHistory(PointHistory pointHistory) {
		pointHistoryRepository.save(pointHistory);
	}

}
