package com.dolphin.platform.dto;

import com.dolphin.platform.model.Attachment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItem {
	private String productName;
	private String productId;
	private Attachment image;
	private String variantId;
	private Double subTotal;
	private Integer orderQuantity;
}
