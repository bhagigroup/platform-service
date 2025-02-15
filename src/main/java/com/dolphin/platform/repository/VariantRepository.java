package com.dolphin.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dolphin.platform.model.Variant;

public interface VariantRepository extends MongoRepository<Variant, String> {

}
