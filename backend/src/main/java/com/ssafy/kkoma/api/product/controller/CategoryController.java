package com.ssafy.kkoma.api.product.controller;

import java.util.List;

import com.ssafy.kkoma.api.product.dto.response.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.kkoma.api.product.service.CategoryService;
import com.ssafy.kkoma.global.util.ApiUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;

	@Tag(name = "Category")
	@Operation(
		summary = "카테고리 리스트 전체 조회",
		security = { @SecurityRequirement(name = "bearer-key") }
	)
	@GetMapping
	public ResponseEntity<ApiUtils.ApiResult<List<CategoryResponse>>> getCategories() {
		List<CategoryResponse> response = categoryService.getCategories();
		return ResponseEntity.ok(ApiUtils.success(response));
	}

	@Tag(name = "Category")
	@Operation(
		summary = "카테고리 id로 조회",
		security = { @SecurityRequirement(name = "bearer-key") }
	)
	@GetMapping("/{categoryId}")
	public ResponseEntity<ApiUtils.ApiResult<CategoryResponse>> getCategory(@PathVariable Integer categoryId) {
		CategoryResponse response = categoryService.getCategory(categoryId);
		return ResponseEntity.ok(ApiUtils.success(response));
	}

}
