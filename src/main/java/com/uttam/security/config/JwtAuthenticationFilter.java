package com.uttam.security.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.uttam.security.entity.UserDO;
import com.uttam.security.service.JWTService;
import com.uttam.security.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	final JWTService jwtservice;
	
	@Autowired
	final UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			
			final String authHeader = request.getHeader("Authorization");
			final String jwt;
			final String userEmail;
			//if token not present at all
			if(authHeader == null || !authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
			//token starts from position 7 "Bearer token_id"
			jwt = authHeader.substring(7);
			userEmail = jwtservice.extractUsername(jwt);
			
			//check if previously authenticated
			if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDO userDO = userService.getUserByEmail(userEmail).get();
				if(userDO != null && jwtservice.isTokenValid(jwt, userDO)) {
					UsernamePasswordAuthenticationToken authenticationToken = 
							new UsernamePasswordAuthenticationToken(userDO, null,userDO.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					//set context for future authentication
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
			filterChain.doFilter(request, response);
	}

}
