package com.ssafy.kkoma.api.area.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.kkoma.api.area.dto.SubArea;
import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.domain.area.repository.AreaRepository;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class AreaService {

	private final AreaRepository areaRepository;

	public List<SubArea> getArea(Long code) {
		int level = code == null ? 0 : codeLevel(code);
		if(level == 4) {
			throw new BusinessException(ErrorCode.SUB_AREA_NOT_EXISTS);
		}

		long digit = levelDigit(level);
		long nextDigit = levelDigit(level + 1);

		return areaRepository.getArea(level, code, digit, nextDigit).stream()
				.map(sub -> SubArea.fromEntity(sub, level))
				.collect(Collectors.toList());
	}


	private long levelDigit(int level) {
		return (long)Math.pow(10, 11 - (level * 3));
	}

	private int codeLevel(long code) {
		for (int i = 1; i < 4; i++) {
			if(code % levelDigit(i) == 0) return i;
		}
		return 4;
	}

	public Area findAreaById(Long id) {
		return areaRepository.findAreaById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.AREA_NOT_EXISTS));
	}

}
