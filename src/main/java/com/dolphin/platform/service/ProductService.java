package com.dolphin.platform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dolphin.platform.dto.CategoryDto;
import com.dolphin.platform.dto.FilterDto;
import com.dolphin.platform.dto.SubcategoryDto;
import com.dolphin.platform.model.Attachment;
import com.dolphin.platform.model.Banners;
import com.dolphin.platform.model.Category;
import com.dolphin.platform.model.Product;
import com.dolphin.platform.model.SubCategory;
import com.dolphin.platform.model.Variant;
import com.dolphin.platform.repository.AttachmentRepository;
import com.dolphin.platform.repository.BannersRepository;
import com.dolphin.platform.repository.CategoryRepository;
import com.dolphin.platform.repository.ProductRepository;
import com.dolphin.platform.repository.SubCategoryRepository;
import com.dolphin.platform.repository.VariantRepository;
import com.dolphin.platform.utility.AWSUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final SubCategoryRepository subCategoryRepository;
	private final AttachmentRepository attachmentRepository;
	private final BannersRepository bannerRepository;
	private final VariantRepository variantRepository;
	private final AWSUtil awsUtil;
	private final MongoTemplate mongoTemplate;
	public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
	
	public List<Product> getProductsWithFilter(FilterDto filter) {
		log.info("Filter object...{}", filter);
		Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();
        if (filter.getName() != null && !filter.getName().isEmpty()) {
        	log.info("hello_name");
            criteriaList.add(Criteria.where("Name").is(filter.getName()));
        }
        if (filter.getCategoryId() != null && !filter.getCategoryId().isEmpty()) {
        	log.info("hello_cat1");
            criteriaList.add(Criteria.where("categoryId").is(filter.getCategoryId()));
        }
        if (filter.getSubCategoryId() != null && !filter.getSubCategoryId().isEmpty()) {
        	log.info("hello_cat2");
            criteriaList.add(Criteria.where("subCategoryId").is(filter.getSubCategoryId()));
        }
        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }
        log.info("query...{}", query);
        return mongoTemplate.find(query, Product.class);
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
    
    public Attachment uploadFile(MultipartFile file, String productId) {
    	try {
    		Map<String, String> response = awsUtil.uploadFile(file);
        	Attachment attachment = Attachment.builder().fileName(response.get("File Name")).fileUrl(response.get("File Url")).build();
        	Attachment savedAttachment = attachmentRepository.save(attachment);
        	Product product = productRepository.findById(productId).get();
        	List<Attachment> productAttachmets = product.getAttachments();
        	if(productAttachmets != null) {
        		productAttachmets.add(savedAttachment);
            	product.setAttachments(productAttachmets);
            	productRepository.save(product);
        	}else {
        		productAttachmets = new ArrayList<>();
        		productAttachmets.add(savedAttachment);
        		product.setAttachments(productAttachmets);
        		productRepository.save(product);
        	}
        	return savedAttachment;
    	} catch (Exception e) {
    		log.error(e.getMessage());
    		return null;
    	}
    }
    
    public Attachment catImageUpload(MultipartFile file, String categoryId) {
    	try {
    		Map<String, String> response = awsUtil.uploadFile(file);
        	Attachment attachment = Attachment.builder().fileName(response.get("File Name")).fileUrl(response.get("File Url")).build();
        	Attachment savedAttachment = attachmentRepository.save(attachment);
        	Category category = categoryRepository.findById(categoryId).get();
        	category.setImage(savedAttachment);
        	categoryRepository.save(category);
        	return savedAttachment;
    	} catch (Exception e) {
    		log.error(e.getMessage());
    		return null;
    	}
    }
    
    public Attachment subCatImageUpload(MultipartFile file, String subCategoryId) {
    	try {
    		Map<String, String> response = awsUtil.uploadFile(file);
        	Attachment attachment = Attachment.builder().fileName(response.get("File Name")).fileUrl(response.get("File Url")).build();
        	Attachment savedAttachment = attachmentRepository.save(attachment);
        	SubCategory subCategory = subCategoryRepository.findById(subCategoryId).get();
        	subCategory.setImage(savedAttachment);
        	subCategoryRepository.save(subCategory);
        	return savedAttachment;
    	} catch (Exception e) {
    		log.error(e.getMessage());
    		return null;
    	}
    }
    
    public Attachment bannerImageUpload(MultipartFile file, String bannerId) {
    	try {
    		Map<String, String> response = awsUtil.uploadFile(file);
        	Attachment attachment = Attachment.builder().fileName(response.get("File Name")).fileUrl(response.get("File Url")).build();
        	Attachment savedAttachment = attachmentRepository.save(attachment);
        	Banners banner = bannerRepository.findById(bannerId).get();
        	banner.setImage(savedAttachment);
        	bannerRepository.save(banner);
        	return savedAttachment;
    	} catch (Exception e) {
    		log.error(e.getMessage());
    		return null;
    	}
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
    
    public Banners saveBanner(Banners banner) {
    	return bannerRepository.save(banner);
    }
    
//    public Product saveAttachment(Attachment attachment) {
//    	Attachment savedAttachment = attachmentRepository.save(attachment);
//    	Product product = productRepository.findById(attachment.getProductId()).get();
//    	List<Attachment> productAttachmets = product.getAttachments();
//    	productAttachmets.add(savedAttachment);
//    	product.setAttachments(productAttachmets);
//    	return product;
//    }
    
    public Variant saveVariant(Variant variant, String productId) {
    	try {
    		Variant savedVariant = variantRepository.save(variant);
    		Product product = productRepository.findById(productId).get();
        	List<Variant> productVariants = product.getVariants();
        	if(productVariants != null) {
        		productVariants.add(savedVariant);
            	product.setVariants(productVariants);
            	productRepository.save(product);
        	}else {
        		productVariants = new ArrayList<>();
        		productVariants.add(savedVariant);
        		product.setVariants(productVariants);
        		productRepository.save(product);
        	}
        	return savedVariant;
    	} catch (Exception e) {
    		log.error(e.getMessage());
    		return null;
    	}
    }
}
