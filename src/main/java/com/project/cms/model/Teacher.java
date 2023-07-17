package com.project.cms.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cms_teachers", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Teacher {
	
	@Id
	@SequenceGenerator(name = "teacher_sequence", sequenceName = "teacher_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "teacher_sequence")
	private Long id;
	
	private String name;
	
	private String dob;
	
	private String gender;
	
	private String qualification;
	
	private String designation;
	
	private String mobile;
	
	@Column(name = "email")
	private String email;
	
	private String emergencyNumber;
	
	private String address;
	
	private String lastWorkingCompany;
	
	private String presentCompany;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date joiningDate;
	
	private String fatherName;
	
}
