package com.ssafy.kkoma.api.image.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.kkoma.api.image.service.ImageService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Image")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

	private final ImageService imageService;

	@PostMapping("/upload")
	ResponseEntity<?> uploadImages(@RequestPart("images") List<MultipartFile> files) {
		List<String> urls = imageService.uploadImages(files);
		return ResponseEntity.ok().body(urls);
	}
}
