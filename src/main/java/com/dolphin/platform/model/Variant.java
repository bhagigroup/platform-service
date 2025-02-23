package com.dolphin.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Variant {
	@Id
    private String id;
	private String name;
    private Double price;
    private String quantity;
    private Integer inventory;
    private String color;
    private String size;
}
