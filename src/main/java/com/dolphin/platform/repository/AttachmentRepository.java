package com.dolphin.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dolphin.platform.model.Attachment;

public interface AttachmentRepository extends MongoRepository<Attachment, String> {

}
