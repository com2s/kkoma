package com.ssafy.kkoma.api.area.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.kkoma.api.area.dto.SubArea;
import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.domain.area.repository.AreaRepository;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import com.ssafy.kkoma.global.util.AreaCodeUtils;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AreaService {

	private final AreaRepository areaRepository;

	public List<SubArea> getArea(Long code) {
		int level = code == null ? 0 : AreaCodeUtils.getLevel(code);
		if(level == 4) {
			throw new BusinessException(ErrorCode.SUB_AREA_NOT_EXISTS);
		}

		long digit = AreaCodeUtils.getDigitByLevel(level);
		long nextDigit = AreaCodeUtils.getDigitByLevel(level + 1);

		return areaRepository.getArea(level, code, digit, nextDigit).stream()
				.map(sub -> SubArea.fromEntity(sub, level))
				.collect(Collectors.toList());
	}

	public Area findAreaById(Long id) {
		return areaRepository.findAreaById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.AREA_NOT_EXISTS));
	}

}
