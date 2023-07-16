package com.project.cms.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;

import com.project.cms.model.Student;
import com.project.cms.model.StudentInfo;
import com.project.cms.model.Teacher;
import com.project.cms.model.TeacherInfo;
import com.project.cms.model.TeachersInfo;
import com.project.cms.model.User;
import com.project.cms.model.UserRegistration;

public interface CmsService {
	
	public UserRegistration saveUser(User user);
	
	public UserRegistration loginUser(String email, String password);
	
	public String deleteUser(String email);

	public UserRegistration changePassword(User user);

	public Teacher addTeacher(TeachersInfo dashboard, HttpServletRequest httpServletRequest);

	public Teacher getTeacher(String email);

	public Student addStudent(StudentInfo studentInfo, HttpHeaders httpHeaders);

	public Student getStudent(String email);
}
