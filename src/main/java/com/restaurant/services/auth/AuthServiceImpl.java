package com.restaurant.services.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurant.dtos.SignupRequest;
import com.restaurant.dtos.UserDto;
import com.restaurant.entities.User;
import com.restaurant.enums.UserRole;
import com.restaurant.repositories.UserRepository;

import jakarta.annotation.PostConstruct;


@Service
public class AuthServiceImpl implements AuthService{

	
	private final UserRepository userRepository;
	 
	    public AuthServiceImpl(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }
	
//	
	@PostConstruct
	public void createAdminAccount() {
	    User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
	    if (adminAccount == null) {
	        User user = new User();
	        user.setName("admin");
	        user.setEmail("admin@gmail.com");
	        user.setPassword(new BCryptPasswordEncoder().encode("admin"));
	        user.setUserRole(UserRole.ADMIN);
	        userRepository.save(user);
	    }
	}

	@Override
	public UserDto createUser(SignupRequest sr) {
		// TODO Auto-generated method stub
		User user=new User();
		user.setName(sr.getName());
		user.setEmail(sr.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(sr.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);
		User createduser=userRepository.save(user);
		UserDto createduserdto=new UserDto();
		createduserdto.setId(createduser.getId());
		createduserdto.setName(createduser.getName());
		createduserdto.setEmail(createduser.getEmail());
		createduserdto.setUserRole(createduser.getUserRole());
		
		return createduserdto;
	}

}
