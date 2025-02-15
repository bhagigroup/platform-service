package com.dolphin.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document
public class Banners {
	@Id
    private String id;
    private String name;
    private String description;
	private String categoryId;
	private Attachment image;
}
