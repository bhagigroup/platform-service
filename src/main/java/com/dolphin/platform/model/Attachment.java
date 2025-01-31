package com.dolphin.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document
public class Attachment {
	@Id
    private String id;
    private String fileUrl;
	private String fileName;
	private String productId;
}
