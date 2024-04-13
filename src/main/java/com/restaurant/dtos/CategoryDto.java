package com.restaurant.dtos;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class CategoryDto {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id ;
	private String name;
	private String description;
	private MultipartFile img;
	@Lob
	@Column(columnDefinition = "longblob")
	private byte[] returnedImg;
}
