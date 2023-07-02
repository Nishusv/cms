package com.project.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.cms.model.Teacher;
import com.project.cms.model.TeacherInfo;
import com.project.cms.model.User;
import com.project.cms.model.UserRegistration;
import com.project.cms.service.CmsServiceImpl;

@RestController
@RequestMapping("/v1/cms")
public class CmsController {

	@Autowired
	private CmsServiceImpl cmsService;

	@PostMapping("/signup")
	public ResponseEntity<UserRegistration> initiateUserRegistration(@RequestBody User user) {
		return ResponseEntity.ok(cmsService.saveUser(user));

	}

	@GetMapping("/login")
	public ResponseEntity<UserRegistration> getLoginDetails(
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password) {
		return ResponseEntity.ok(cmsService.loginUser(email, password));
	}

	@DeleteMapping("/remove-user")
	public ResponseEntity<String> deleteUser(@RequestParam(value = "email") String email) {
		return ResponseEntity.ok(cmsService.deleteUser(email));
	}

	@PutMapping("/change-password")
	public ResponseEntity<UserRegistration> changePassword(@RequestBody User user) {
		return ResponseEntity.ok(cmsService.changePassword(user));
	}
	
	@PostMapping("/add-teacher")
	public ResponseEntity<Teacher> addTeacher(@RequestBody TeacherInfo teacher,
		HttpHeaders httpHeaders){
		return ResponseEntity.ok(cmsService.addTeacher(teacher, httpHeaders));
	}
	
	@GetMapping("/get-teacher")
	public ResponseEntity<Teacher> getTeacher(@RequestParam(value = "email") String email){
		return ResponseEntity.ok(cmsService.getTeacher(email));
	}

}
