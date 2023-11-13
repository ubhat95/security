package com.uttam.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
	ADMIN("admin",1),
	USER("user", 2);
	
	private String name;
	private Integer id;
}
