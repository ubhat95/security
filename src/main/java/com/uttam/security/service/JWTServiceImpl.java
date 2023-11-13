package com.uttam.security.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService {
	
	private static final String SECRET_KEY ="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

	@Override
	public String extractUsername(String jwtToken) {
		return extractClaim(jwtToken, Claims::getSubject);
	}	
	
	@Override
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	
	@Override
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000* 60*24)) // valid for a day
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	@Override
	public Boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claim = extractAllClaims(token);
		return claimResolver.apply(claim);
		
 	}
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		
	}

	private Key getSignInKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
	}

	


	
	
}
