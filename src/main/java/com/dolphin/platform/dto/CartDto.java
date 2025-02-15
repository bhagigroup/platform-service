package com.dolphin.platform.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDto {
	private List<CartItem> cartItems;
	private Double total;
}
