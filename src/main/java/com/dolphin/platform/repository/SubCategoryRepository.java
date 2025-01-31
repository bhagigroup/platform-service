package com.dolphin.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dolphin.platform.model.SubCategory;
import java.util.List;


public interface SubCategoryRepository extends MongoRepository<SubCategory, String> {
	
	List<SubCategory> findByCategoryId(String categoryId);

}
