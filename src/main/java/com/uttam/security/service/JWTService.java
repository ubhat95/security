package com.uttam.security.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
	String extractUsername(String jwtToken);
	
	String generateToken(Map<String, Object> extraClaims, UserDetails userDetails );
	
	String generateToken(UserDetails userDetails );
	
	Boolean isTokenValid(String token, UserDetails userDetails);
}
