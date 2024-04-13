package com.restaurant.services.admin;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.restaurant.dtos.CategoryDto;
import com.restaurant.entities.Category;
import com.restaurant.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final CategoryRepository categoryRepository;

	@Override
	public void postcategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CategoryDto postCategory(CategoryDto categoryDto) throws IOException{
		// TODO Auto-generated method stub
		Category category=new Category();
		category.setName(categoryDto.getDescription());
		category.setDescription(categoryDto.getDescription());
		try {
			category.setImg(categoryDto.getImg().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Category createdCategory=categoryRepository.save(category);
		CategoryDto createCategoryDto=new CategoryDto();
		
		createCategoryDto.setId(createdCategory.getId());
		return createCategoryDto;
	}

	

	
}
