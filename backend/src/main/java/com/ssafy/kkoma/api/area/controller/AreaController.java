package com.ssafy.kkoma.api.area.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.kkoma.api.area.dto.SubArea;
import com.ssafy.kkoma.api.area.service.AreaService;
import com.ssafy.kkoma.domain.area.entity.Area;
import com.ssafy.kkoma.global.util.ApiUtils;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Area")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/area")
public class AreaController {

	private final AreaService areaService;

	@GetMapping
	public ResponseEntity<ApiUtils.ApiResult<List<SubArea>>> getArea(@RequestParam(required = false) Long code) {
		List<SubArea> response = areaService.getArea(code);
		return ResponseEntity.ok(ApiUtils.success(response));
	}

}
