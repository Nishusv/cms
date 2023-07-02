package com.project.cms.service;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.project.cms.model.User;
import com.project.cms.model.UserRegistration;
import com.project.cms.repository.UserRepository;

@Service
public class CmsServiceImpl implements CmsService {

	@Autowired
	private UserRepository userRepository;

	private Encoder encode = Base64.getEncoder();

	private Decoder decoder = Base64.getDecoder();

	@Override
	public UserRegistration saveUser(User user) {

		if (user.getPassword().equals(user.getConfirmPassword())) {
			UserRegistration userRegistration = new UserRegistration();
			userRegistration.setEmail(user.getEmail());
			userRegistration.setFullName(user.getFullName());
			userRegistration.setPassword(encode.encodeToString(user.getPassword().getBytes()));
			userRegistration.setIsActive(true);
			String concatCode = user.getFullName() + user.getEmail();
			String authorization = encode.encodeToString(concatCode.getBytes());
			userRegistration.setAuthorization(authorization);
			return userRepository.save(userRegistration);
		} else {
			throw new ApplicationContextException("password and confirm password should be identical");
		}

	}

	public UserRegistration getUser(String emailId) {
		return userRepository.findByEmail(emailId);
	}

	public String deleteUser(String emailId) {
		UserRegistration dbResponse = userRepository.findByEmail(emailId);
		userRepository.delete(dbResponse);
		return "user removed successfully";

	}

	public UserRegistration changePassword(User user) {

		if (user.getPassword().equals(user.getConfirmPassword())) {
			UserRegistration dbResponse = userRepository.findByEmail(user.getEmail());
			String dbPassword = new String(decoder.decode(dbResponse.getPassword()));
			if (dbPassword.equals(user.getOldPassword())) {
				Encoder encode = Base64.getEncoder();
				String password = encode.encodeToString(user.getPassword().getBytes());
				dbResponse.setPassword(password);
				return userRepository.save(dbResponse);
			} else {
				throw new ApplicationContextException("Old password is not matching with existing password");
			}
		} else {
			throw new ApplicationContextException("password and confirm password should be identical");
		}

	}

	@Override
	public UserRegistration loginUser(String email, String password) {
		UserRegistration user = getUser(email);
		String decodePassword = new String(decoder.decode(user.getPassword()));
		if (decodePassword.equals(password)) {
			if (user.getIsActive().equals(Boolean.TRUE)) {
				return user;
			} else {
				throw new ApplicationContextException("User is inactive please Signup again");
			}
		} else {
			throw new ApplicationContextException("Password is incorrect");
		}
	}

}
