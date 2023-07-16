package com.project.cms.model;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_leave_details_id")
	private LeaveDetails leaveDetails;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_salary_info_id")
	private List<SalaryInfo> salaryInfo;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_attendance_info_id")
	private AttendanceInfo attendanceInfo;

}
