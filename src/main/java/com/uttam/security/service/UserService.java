package com.uttam.security.service;

import java.util.Optional;

import com.uttam.security.entity.UserDO;

public interface UserService {
	 
	Optional<UserDO> getUserByEmail(String email);
}
