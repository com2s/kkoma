package com.ssafy.kkoma.global.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class S3Util {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    public List<String> uploadFiles(List<MultipartFile> files) {
        return files.stream()
            .map(this::uploadFile)
            .collect(Collectors.toList());
    }

    public String uploadFile(MultipartFile file) {

        if(file.isEmpty()) {
            throw new BusinessException(ErrorCode.IMAGE_NOT_EXISTS);
        }

        String fileName = createStoreFileName("images", file.getOriginalFilename());
        String fileUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            amazonS3Client.putObject(new PutObjectRequest(
                    bucket, fileName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException e) {
            throw new BusinessException(ErrorCode.S3UPLOAD_FAIL);
        }
        return fileUrl;
    }

    private String createStoreFileName(String directory, String originalName) {
        String ext = extractExt(originalName);
        String uuid = UUID.randomUUID().toString();
        return directory + "/" + uuid + "." + ext;
    }

    private static String extractExt(String originalName) {
        int pos = originalName.lastIndexOf(".");
        return originalName.substring(pos + 1);
    }

}

