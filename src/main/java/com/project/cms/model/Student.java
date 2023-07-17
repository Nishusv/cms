package com.project.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cms_students", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Student {
	
	@Id
	@SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "student_sequence")
	private Long id;
	
	private String name;
	
	private String dob;
	
	private String gender;
	
	private String mobile;
	
	@Column(name = "email")
	private String email;
	
	private String address;
	
	private String fatherName;
	
}
