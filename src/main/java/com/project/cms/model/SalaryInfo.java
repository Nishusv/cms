package com.project.cms.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "salary_info")
@Builder
public class SalaryInfo {
	
	@Id
	@SequenceGenerator(name = "salary_info_sequence", sequenceName = "salary_info_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "salary_info_sequence")
	private Long id;
	
	private String bankAccount;
	
	private String bankName;
	
	private String deduction;
	
	private String earnings;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date salaryDate;
	
	private String netSalary;
	
	private String name;
	
	private String grossSalary;
	
	private String totalDeduction;
	
	private String month;
	
	@OneToOne(targetEntity = Teacher.class, cascade = CascadeType.ALL)
	@JoinColumn(name="fk_teacher_id", referencedColumnName = "id")
	private Teacher teacher;
	

}
