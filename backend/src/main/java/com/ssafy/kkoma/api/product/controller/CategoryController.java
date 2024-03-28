package com.ssafy.kkoma.api.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.kkoma.api.product.service.CategoryService;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.global.util.ApiUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<ApiUtils.ApiResult<List<Category>>> getCategories() {
		List<Category> response = categoryService.getCategories();
		return ResponseEntity.ok(ApiUtils.success(response));
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<ApiUtils.ApiResult<Category>> getCategory(@PathVariable Integer categoryId) {
		Category response = categoryService.findCategoryById(categoryId);
		return ResponseEntity.ok(ApiUtils.success(response));
	}

}
