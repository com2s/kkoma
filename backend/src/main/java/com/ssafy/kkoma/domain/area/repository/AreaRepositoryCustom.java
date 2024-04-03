package com.ssafy.kkoma.domain.area.repository;

import java.util.List;

import com.ssafy.kkoma.domain.area.entity.Area;

public interface AreaRepositoryCustom {

	List<Area> getArea(int level, Long code, Long digit, Long nextDigit);

}
