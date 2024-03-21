package com.ssafy.kkoma.api.image.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.kkoma.global.util.S3Util;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final S3Util s3Util;

	public List<String> uploadImages(List<MultipartFile> files) {
		return s3Util.uploadFiles(files);
	}
}
