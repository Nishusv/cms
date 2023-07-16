package com.project.cms.model;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardInfo2 {
	
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

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date joiningDate;

	private String fatherName;
}
