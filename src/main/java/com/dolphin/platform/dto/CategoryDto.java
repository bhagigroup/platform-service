package com.dolphin.platform.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDto {
	private String id;
    private String name;
    private List<SubcategoryDto> subcategories;
}
