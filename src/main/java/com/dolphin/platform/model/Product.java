package com.dolphin.platform.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document(collection = "f_products")
public class Product {
	@Id
    private String id;
	@JsonProperty("Name")
    private String name;
	@JsonProperty("Description")
    private String description;
	@JsonProperty("Type")
    private String type;
	@JsonProperty("Color")
    private String color;
	@JsonProperty("Size")
    private String size;
	@JsonProperty("Material")
    private String material;
	@JsonProperty("Discount Price")
    private double discount_price;
	@JsonProperty("Price")
    private double price;
	@JsonProperty("Quantity")
	private String quantity;
	@JsonProperty("Inventory")
	private int inventory;
	private String categoryId;
	private String subCategoryId;
	private List<Attachment> attachments;
}
