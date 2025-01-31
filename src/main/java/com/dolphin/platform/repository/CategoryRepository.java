package com.dolphin.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dolphin.platform.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {

}
