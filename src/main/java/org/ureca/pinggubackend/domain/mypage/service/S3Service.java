package org.ureca.pinggubackend.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadImageFromUrl(String imageUrl) throws IOException {
        // 파일 이름 설정
        String fileName = UUID.randomUUID() + ".jpg";

        // URL에서 이미지 읽어오기
        URL url = new URL(imageUrl);
        try (InputStream inputStream = url.openStream()) {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType("image/jpeg") // 필요 시 contentType 추론 가능
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(inputStream, url.openConnection().getContentLength()));

            return "https://%s.s3.us-east-1.amazonaws.com/%s".formatted(bucketName, fileName);
        }
    }
}
