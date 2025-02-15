package com.dolphin.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dolphin.platform.model.Banners;

public interface BannersRepository extends MongoRepository<Banners, String> {

}
