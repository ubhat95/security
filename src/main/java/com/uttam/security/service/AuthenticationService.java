package com.uttam.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uttam.security.dto.AuthenticationRequest;
import com.uttam.security.dto.AuthenticationResponse;
import com.uttam.security.dto.RegisterRequest;
import com.uttam.security.entity.UserDO;
import com.uttam.security.enums.Role;
import com.uttam.security.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JWTService jwtService;
	
	@Autowired
	AuthenticationManager authenticationManager;

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		UserDO userDO = userRepository.findByEmail(request.getEmail()).get();
		String jwtTokeString  = jwtService.generateToken(userDO);
		return AuthenticationResponse.builder().token(jwtTokeString).build();
	}

	public AuthenticationResponse register(RegisterRequest request) {
		UserDO userDO = UserDO.builder()
						.email(request.getEmail())
						.firstname(request.getFirstname())
						.lastname(request.getLastname())
						.password(passwordEncoder.encode(request.getPassword()))
						.role(Role.getById(request.getRoleId()))
						.build();
		
		userRepository.save(userDO);
		
		String jwtTokeString  = jwtService.generateToken(userDO);
		return AuthenticationResponse.builder().token(jwtTokeString).build();
		
	}

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

}
