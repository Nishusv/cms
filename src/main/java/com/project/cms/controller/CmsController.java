package com.project.cms.controller;

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

import com.project.cms.model.DashboardInfo2;
import com.project.cms.model.Student;
import com.project.cms.model.StudentInfo;
import com.project.cms.model.Teacher;
import com.project.cms.model.TeacherInfo;
import com.project.cms.model.TeachersInfo;
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
		return new ResponseEntity<>(cmsService.saveUser(user),HttpStatus.CREATED);

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
	
//	@PostMapping("/add-teacher")
//	public ResponseEntity<Teacher> addTeacher(@RequestBody DashboardInfo2 teacher,
//		HttpHeaders httpHeaders){
//		String n = "";
//		return null;
////		return ResponseEntity.ok(cmsService.addTeacher(teacher, httpHeaders));
//	}
	
	@PostMapping("/add-teacher")
	public ResponseEntity<Teacher> addTeacher(@RequestBody TeachersInfo dashboard, HttpServletRequest httpServletRequest){
		System.out.println(dashboard);
		
		return ResponseEntity.ok(cmsService.addTeacher(dashboard, httpServletRequest));
	}
	
//	@PostMapping("/add-teachers")
//	public ResponseEntity<Teacher> addTeachers(@RequestBody TeacherInfo teacher,
//		HttpHeaders httpHeaders){
//		String n = "";
//		return ResponseEntity.ok(cmsService.addTeacher(teacher, httpHeaders));
//	}
//	
	
	@GetMapping("/get-teacher")
	public ResponseEntity<Teacher> getTeacher(@RequestParam(value = "email") String email){
		return ResponseEntity.ok(cmsService.getTeacher(email));
	}
	
	@PostMapping("/add-student")
	public ResponseEntity<Student> addStudent(@RequestBody StudentInfo studentInfo,
		HttpHeaders httpHeaders){
		return ResponseEntity.ok(cmsService.addStudent(studentInfo, httpHeaders));
	}
	
	@GetMapping("/get-student")
	public ResponseEntity<Student> getStudent(@RequestParam(value = "email") String email){
		return ResponseEntity.ok(cmsService.getStudent(email));
	}

}
