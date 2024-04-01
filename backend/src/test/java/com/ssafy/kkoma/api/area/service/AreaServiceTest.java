package com.ssafy.kkoma.api.area.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.factory.AreaFactory;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;

@SpringBootTest
class AreaServiceTest {

	@Autowired
	AreaFactory areaFactory;

	@Autowired
	AreaService areaService;

	@Test
	@Transactional
	public void 올바른_법정_코드로_지역_정보_조회() throws Exception{
	    // given
		Area savedArea = areaFactory.createArea();
		final Long REGIONCODE = 1111111L;

	    // when
		Area area = areaService.findAreaById(REGIONCODE);

		// then
		assertEquals("서울특별시", area.getSubName1());
	}

	@Test
	@Transactional
	public void 존재하지_않는_법정_코드로_지역_정보를_조회하면_예외를_던진다() throws Exception{
	    // given
		Area savedArea = areaFactory.createArea();

	    // when & then
		assertThrows(EntityNotFoundException.class, () -> areaService.findAreaById(00000000000L));
	}


}
