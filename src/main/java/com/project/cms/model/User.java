package com.project.cms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	
	private String fullName;
	private String email;
	private String password;
	private String confirmPassword;
	private String oldPassword;
	private String role;

}
