package com.dolphin.platform.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dolphin.platform.dto.CategoryDto;
import com.dolphin.platform.dto.FilterDto;
import com.dolphin.platform.model.Attachment;
import com.dolphin.platform.model.Banners;
import com.dolphin.platform.model.Category;
import com.dolphin.platform.model.Product;
import com.dolphin.platform.model.SubCategory;
import com.dolphin.platform.model.Variant;
import com.dolphin.platform.service.ProductService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
@Slf4j
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @GetMapping("all-products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    @PostMapping("products-by-filter")
    public List<Product> getProductsWithFilter(@RequestBody FilterDto filter) {
    	log.info("filter");
        return productService.getProductsWithFilter(filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create-product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/upload")
    public ResponseEntity<Attachment> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("productId") String productId) {
        try {
        	log.info("hello...");
        	Attachment response = productService.uploadFile(file, productId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
	/*
	 * public ResponseEntity<Product> saveAttachment(@RequestBody Attachment
	 * attachment){ Product createdProduct =
	 * productService.saveAttachment(attachment); return
	 * ResponseEntity.status(HttpStatus.OK).body(createdProduct); }
	 */
    
    @PostMapping("/create-category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = productService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
    
    @PostMapping("/create-sub-category")
    public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategory subCategory) {
        SubCategory createdSubCategory = productService.createSubCategory(subCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubCategory);
    }
    
    @GetMapping("all-categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllCategories());
    }

    @GetMapping("get-categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getCategories());
    }
    
    @GetMapping("get-sub-categories/{id}")
    public ResponseEntity<List<SubCategory>> getSubCategories(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getSubCategories(id));
    }
    
    @PostMapping("/save-banner")
    public ResponseEntity<Banners> createBanner(@RequestBody Banners banner) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.saveBanner(banner));
    }
    
    @PostMapping("/cat-image-upload")
    public ResponseEntity<Attachment> categoryImageUpload(@RequestParam("file") MultipartFile file, @RequestParam("categoryId") String categoryId) {
        try {
        	Attachment response = productService.catImageUpload(file, categoryId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    @PostMapping("/subcat-image-upload")
    public ResponseEntity<Attachment> subCategoryImageUpload(@RequestParam("file") MultipartFile file, @RequestParam("categoryId") String subCategoryId) {
        try {
        	Attachment response = productService.subCatImageUpload(file, subCategoryId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @PostMapping("/banner-image-upload")
    public ResponseEntity<Attachment> bannerImageUpload(@RequestParam("file") MultipartFile file, @RequestParam("categoryId") String bannerId) {
        try {
        	Attachment response = productService.bannerImageUpload(file, bannerId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    @PostMapping("/save-variant/{productId}")
    public ResponseEntity<Variant> saveVariant(@RequestBody Variant variant, @PathVariable String productId) {
        try {
        	log.info("productId...{}", productId);
        	Variant response = productService.saveVariant(variant, productId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
}
