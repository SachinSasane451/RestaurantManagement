package com.restaurant.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dtos.AuthenticationRequest;
import com.restaurant.dtos.AuthenticationResponse;
import com.restaurant.dtos.SignupRequest;
import com.restaurant.dtos.UserDto;
import com.restaurant.entities.User;
import com.restaurant.repositories.UserRepository;
import com.restaurant.services.auth.AuthService;
import com.restaurant.services.auth.jwt.UserDetailsServiceImpl;
import com.restaurant.services.auth.jwt.UserService;
import com.restaurant.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	
	private final AuthService AuthService;
	
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	
	private final UserService userService;
	
	private final AuthenticationManager authenticationManager;
	
//	public AuthController(AuthService AuthService ,AuthenticationManager authenticationManager,UserService userService, JwtUtil jwtUtil, UserRepository userRepository) {
//		this.AuthService=AuthService;
//		this.jwtUtil = jwtUtil;
//		this.userRepository = userRepository;
//		this.authenticationManager=authenticationManager;
//		this.userService=userService;
//	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest sr){
		
		UserDto createdUserdto=AuthService.createUser(sr);
		if(createdUserdto ==null) {
			return new ResponseEntity<>("user not created succesfully", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(createdUserdto,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException {
	    try {
	        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
	    } catch (BadCredentialsException e) {
	        throw new BadCredentialsException("Incorrect username or password");
	    } catch (DisabledException disabledException) {
	        response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not active");
	        return null;
	    }
	    final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
	    final String jwt = jwtUtil.generateToken(userDetails);
	    Optional<User> optionalUser=userRepository.findFirstByEmail(userDetails.getUsername());
	    AuthenticationResponse authenticationResponse=new AuthenticationResponse();
	    if(optionalUser.isPresent()) {
	    	authenticationResponse.setJwt(jwt);
	    	authenticationResponse.setUserRole(optionalUser.get().getUserRole());
	    	authenticationResponse.setUserId(optionalUser.get().getId());
	    }
	    return authenticationResponse;
	}

	
}
