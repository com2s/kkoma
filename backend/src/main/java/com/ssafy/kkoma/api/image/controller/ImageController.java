package com.ssafy.kkoma.api.image.controller;

import java.util.List;

import com.ssafy.kkoma.global.util.ApiUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.kkoma.api.image.service.ImageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Image")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

	private final ImageService imageService;

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "to upload images", security = { @SecurityRequirement(name = "bearer-key") })
	ResponseEntity<ApiUtils.ApiResult<List<String>>> uploadImages(
			@Parameter(description = "multipart/form-data 형식의 이미지 리스트를 input으로 받습니다. 업로드 할 수 있는 최대 용량은 100MB 입니다.")
			@RequestPart("images") List<MultipartFile> files) {
		List<String> urls = imageService.uploadImages(files);
		return ResponseEntity.ok().body(ApiUtils.success(urls));
	}
}
