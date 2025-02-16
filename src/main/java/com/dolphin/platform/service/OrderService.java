package com.dolphin.platform.service;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.dolphin.platform.dto.CartDto;
import com.dolphin.platform.dto.CartItem;
import com.dolphin.platform.model.Product;
import com.dolphin.platform.model.Variant;
import com.dolphin.platform.repository.AttachmentRepository;
import com.dolphin.platform.repository.BannersRepository;
import com.dolphin.platform.repository.CategoryRepository;
import com.dolphin.platform.repository.ProductRepository;
import com.dolphin.platform.repository.VariantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
	private final ProductRepository productRepository;
	private final VariantRepository variantRepository;

	public CartDto addToCart(String productId, String variantId, Integer quantity) {
		Product product = productRepository.findById(productId).get();
		Variant variant = variantRepository.findById(variantId).get();
		CartItem cartItem = CartItem.builder().productName(product.getName()).productId(productId)
				.image(product.getAttachments().get(0)).orderQuantity(quantity).subTotal(quantity * variant.getPrice())
				.build();
		CartDto cartDto = CartDto.builder().cartItems(List.of(cartItem)).build();
		return cartDto;
	}
}
