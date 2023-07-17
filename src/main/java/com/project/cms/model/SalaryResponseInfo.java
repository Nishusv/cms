package com.project.cms.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class SalaryResponseInfo {
	
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
	
}
