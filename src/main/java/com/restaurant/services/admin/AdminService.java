package com.restaurant.services.admin;

import java.io.IOException;

import com.restaurant.dtos.CategoryDto;

public interface AdminService {

	void postcategory(CategoryDto categoryDto);

	CategoryDto postCategory(CategoryDto categoryDto) throws IOException;

}
