package com.uttam.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uttam.security.entity.UserDO;

public interface UserRepository extends JpaRepository<UserDO,Integer> {
	
	Optional<UserDO> findByEmail(String email);

}
