package com.dolphin.platform.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dolphin.platform.dto.CategoryDto;
import com.dolphin.platform.dto.SubcategoryDto;
import com.dolphin.platform.model.Attachment;
import com.dolphin.platform.model.Category;
import com.dolphin.platform.model.Product;
import com.dolphin.platform.model.SubCategory;
import com.dolphin.platform.repository.AttachmentRepository;
import com.dolphin.platform.repository.CategoryRepository;
import com.dolphin.platform.repository.ProductRepository;
import com.dolphin.platform.repository.SubCategoryRepository;
import com.dolphin.platform.utility.AWSUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final SubCategoryRepository subCategoryRepository;
	private final AttachmentRepository attachmentRepository;
	private final AWSUtil awsUtil;
	private final MongoTemplate mongoTemplate;
	public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
    	
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
    
    public Attachment uploadFile(MultipartFile file) {
    	Map<String, String> response = awsUtil.uploadFile(file);
    	Attachment attachment = Attachment.builder().fileName(response.get("File Name")).fileUrl(response.get("File Url")).build();
    	Attachment savedAttachment = attachmentRepository.save(attachment);
    	return savedAttachment;
    }
    
    
    public Category createCategory(Category product) {
        return categoryRepository.save(product);
    }
    
    public SubCategory createSubCategory(SubCategory product) {
        return subCategoryRepository.save(product);
    }
    
    public List<CategoryDto> getAllCategories() {
    	List<Category> categories = categoryRepository.findAll();
    	return categories.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());   
    }
    public List<Category> getCategories() {
    	List<Category> categories = categoryRepository.findAll();
    	return categories;
    }
    
    public List<SubCategory> getSubCategories(String id) {
    	List<SubCategory> subcategoryDtos = subCategoryRepository.findByCategoryId(id);
    	return subcategoryDtos;
    }
    
    private CategoryDto mapToDto(Category category) {
        List<SubcategoryDto> subcategoryDtos = subCategoryRepository.findByCategoryId(category.getId()).stream()
            .map(subcategory -> new SubcategoryDto(
                subcategory.getId(),
                subcategory.getName()))
            .collect(Collectors.toList());
        return new CategoryDto(category.getId(), category.getName(), subcategoryDtos);
    }
    
    public Product saveAttachment(Attachment attachment) {
    	Attachment savedAttachment = attachmentRepository.save(attachment);
    	Product product = productRepository.findById(attachment.getProductId()).get();
    	List<Attachment> productAttachmets = product.getAttachments();
    	productAttachmets.add(savedAttachment);
    	product.setAttachments(productAttachmets);
    	return product;
    }
}
