package com.uttam.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uttam.security.entity.UserDO;
import com.uttam.security.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Optional<UserDO> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
