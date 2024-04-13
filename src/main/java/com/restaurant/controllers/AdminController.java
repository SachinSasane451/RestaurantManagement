package com.restaurant.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dtos.CategoryDto;
import com.restaurant.services.admin.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	
	
	@PostMapping("/category")
	public ResponseEntity<CategoryDto> postCategory(@RequestBody CategoryDto categoryDto ) throws IOException{
		
		System.out.println(categoryDto);
		
		CategoryDto createdcategoryDto=adminService.postCategory(categoryDto);
		if(createdcategoryDto==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(createdcategoryDto);
		

	}
}
