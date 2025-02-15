package com.dolphin.platform.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dolphin.platform.dto.OrderItem;

import lombok.Data;

@Data
@Document
public class Order {
	@Id
	private String id;
	private List<OrderItem> orderItem;
	private Double total;
}
