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
		final Long REGIONCODE = 12L;

		Area area = new Area(REGIONCODE, "테스트12시", "테스트12구", "테스트12동", null);

		return areaRepository.save(area);
	}
}
