package com.project.cms.model;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class DashboardInfo {

	private String name;

	private String dob;

	private String gender;

	private String qualification;

	private String designation;

	private String mobile;

	private String email;

	private String emergencyNumber;

	private String address;

	private String lastWorkingCompany;
	
	private String presentWorkingCompany;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date joiningDate;
	
	private String fatherName;

}
