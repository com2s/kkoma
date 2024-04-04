package com.ssafy.kkoma.api.area.controller;

import com.ssafy.kkoma.api.area.dto.SubArea;
import com.ssafy.kkoma.api.area.service.AreaService;
import com.ssafy.kkoma.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Area")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/area")
public class AreaController {

	private final AreaService areaService;

	@Tag(name = "Area")
	@Operation(
			summary = "법정동 리스트 전체 조회",
			security = { @SecurityRequirement(name = "bearer-key") }
	)
	@GetMapping
	public ResponseEntity<ApiUtils.ApiResult<List<SubArea>>> getArea(
		@RequestParam(required = false, name = "code") Long code
	) {
		List<SubArea> response = areaService.getArea(code);
		return ResponseEntity.ok(ApiUtils.success(response));
	}

}
