package com.ssafy.kkoma.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.domain.area.repository.AreaRepository;

@Component
public class AreaFactory {

	@Autowired
	AreaRepository areaRepository;

	public Area createArea() {
		final Long REGIONCODE = 1111111L;

		Area area = new Area(REGIONCODE, "서울특별시", "종로구", "청운동", "");

		return areaRepository.save(area);
	}
}
