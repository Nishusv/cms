package com.project.cms.model;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private String name;
	
	private String dob;
	
	private String gender;
	
	private String mobile;
	
	@Column(name = "email")
	private String email;
	
	private String address;
	
	private String fatherName;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_student_attendance_info_id")
	private StudentAttendanceInfo attendanceInfo;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_sem_info_id")
	private SemInfo semInfo; 

}
