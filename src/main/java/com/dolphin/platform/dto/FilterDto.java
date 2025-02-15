package com.dolphin.platform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterDto {
	private String name;
	private String categoryId;
	private String subCategoryId;
}
