package com.project.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.cms.model.Student;
import com.project.cms.model.StudentInfo;
import com.project.cms.model.Teacher;
import com.project.cms.model.TeacherInfo;
import com.project.cms.model.User;
import com.project.cms.model.UserRegistration;
import com.project.cms.service.CmsService;

@RestController
@RequestMapping("/v1/cms")
public class CmsController {

	@Autowired
	private CmsService cmsService;

	@PostMapping("/signup")
	public ResponseEntity<UserRegistration> initiateUserRegistration(@RequestBody @Validated User user) {
		return new ResponseEntity<>(cmsService.saveUser(user), HttpStatus.CREATED);

	}

	@GetMapping("/login")
	public ResponseEntity<UserRegistration> getLoginDetails(@RequestParam(value = "email") String email,
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
	public ResponseEntity<TeacherInfo> addTeacher(@RequestBody TeacherInfo dashboard,
			HttpServletRequest httpServletRequest) {
		return ResponseEntity.ok(cmsService.addTeacher(dashboard, httpServletRequest));
	}

	@GetMapping("/fetch-all")
	public ResponseEntity<List<UserRegistration>> getAll() {
		return ResponseEntity.ok(cmsService.getAll());
	}

	@GetMapping("/get-teacher")
	public ResponseEntity<TeacherInfo> getTeacher(@RequestParam(value = "email") String email) {
		return ResponseEntity.ok(cmsService.getTeacher(email));
	}

	@PostMapping("/add-student")
	public ResponseEntity<StudentInfo> addStudent(@RequestBody StudentInfo studentInfo, HttpServletRequest httpHeaders) {
		return ResponseEntity.ok(cmsService.addStudent(studentInfo, httpHeaders));
	}

	@GetMapping("/get-student")
	public ResponseEntity<StudentInfo> getStudent(@RequestParam(value = "email") String email) {
		return ResponseEntity.ok(cmsService.getStudent(email));
	}

}
