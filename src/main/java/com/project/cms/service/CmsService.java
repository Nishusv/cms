package com.project.cms.service;

import com.project.cms.model.User;
import com.project.cms.model.UserRegistration;

public interface CmsService {
	
	public UserRegistration saveUser(User user);
	
	public UserRegistration loginUser(String email, String password);
	
	public String deleteUser(String email);
}
