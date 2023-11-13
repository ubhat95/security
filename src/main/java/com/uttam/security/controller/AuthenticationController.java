package com.uttam.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uttam.security.dto.AuthenticationRequest;
import com.uttam.security.dto.AuthenticationResponse;
import com.uttam.security.dto.RegisterRequest;
import com.uttam.security.service.AuthenticationService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	@Autowired
	@Setter
	private AuthenticationService authenticationService;
	
	
	
	@PostMapping("/register")
	public AuthenticationResponse register(@RequestBody RegisterRequest request){
		return authenticationService.register(request);
		
	}
	
	@PostMapping("/authenticate")
	public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
		return authenticationService.authenticate(request);
		
	}
	
	
	@PostMapping("/refresh-token")
	public void refreshToken( HttpServletRequest request,HttpServletResponse response
	  ) throws IOException {
		authenticationService.refreshToken(request, response);
	  }
}