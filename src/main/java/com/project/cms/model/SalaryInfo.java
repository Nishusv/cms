package com.project.cms.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private String bankAccount;
	
	private String bankName;
	
	private String deduction;
	
	private String earnings;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date salaryDate;
	
	private String netSalary;
	
	private String name;
	
	private String grossSalary;
	
	private String totalDeduction;
	
	private String month;
	

}
