package com.dolphin.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Variant {
	@Id
    private String id;
	private String productId;
    private Double price;
    private Integer quantityAvailable;
}
