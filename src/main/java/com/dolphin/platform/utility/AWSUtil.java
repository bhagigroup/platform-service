package com.dolphin.platform.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AWSUtil {
	private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;
    
    public AWSUtil(@Value("${cloud.aws.region.static}") String region, @Value("${cloud.aws.credentials.access-key}") String accessKey, @Value("${cloud.aws.credentials.secret-key}") String secretKey) {
    	this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    public Map<String, String> uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        log.info("filename...{}", fileName);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        String fileUrl = null;
        try {
        	PutObjectRequest request = new PutObjectRequest(this.bucketName, fileName, file.getInputStream(), metadata);
            s3Client.putObject(request);
            fileUrl = s3Client.getUrl(this.bucketName, fileName).toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        Map<String, String> response = new HashMap<>();
        response.put("File Url", fileUrl);
        response.put("File Name", fileName);
        
        return response;
    }
}
