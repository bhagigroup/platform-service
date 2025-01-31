package com.dolphin.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class SubCategory {
	@Id
    private String id;
	private String name;
	private Attachment image;
	private String categoryId;
}
