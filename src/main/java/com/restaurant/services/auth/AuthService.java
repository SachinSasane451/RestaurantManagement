package com.restaurant.services.auth;

import org.springframework.stereotype.Service;

import com.restaurant.dtos.SignupRequest;
import com.restaurant.dtos.UserDto;
import com.restaurant.entities.User;


public interface AuthService {

	UserDto createUser(SignupRequest sr);

}
