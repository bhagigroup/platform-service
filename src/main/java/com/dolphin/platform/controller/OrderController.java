package com.dolphin.platform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dolphin.platform.dto.CartDto;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
	@PostMapping("/add-to-cart/product/{productId}/variant/{variantId}/quantity/{quantity}")
	public ResponseEntity<CartDto> addToCart(@PathVariable String productId, @PathVariable String variantId, @PathVariable Integer quantity) {
		return null;
	}

}
