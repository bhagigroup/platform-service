package com.dolphin.platform.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dolphin.platform.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
	Optional<Product> findById(String id);
}
