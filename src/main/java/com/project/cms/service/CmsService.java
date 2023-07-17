package com.project.cms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;

import com.project.cms.model.Student;
import com.project.cms.model.StudentInfo;
import com.project.cms.model.Teacher;
import com.project.cms.model.TeacherInfo;
import com.project.cms.model.User;
import com.project.cms.model.UserRegistration;

public interface CmsService {
	
	public UserRegistration saveUser(User user);
	
	public UserRegistration loginUser(String email, String password);
	
	public String deleteUser(String email);

	public UserRegistration changePassword(User user);

	public TeacherInfo addTeacher(TeacherInfo dashboard, HttpServletRequest httpServletRequest);

	public TeacherInfo getTeacher(String email);

	public StudentInfo addStudent(StudentInfo studentInfo, HttpServletRequest httpHeaders);

	public StudentInfo getStudent(String email);

	public List<UserRegistration> getAll();
}
