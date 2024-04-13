package com.restaurant.dtos;

import com.restaurant.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {

	private Long id;
	private String name;
	private String email;
	private String password;
	private UserRole userRole;
}
