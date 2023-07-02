package com.project.cms.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "leave_details")
@Embeddable
@Builder
public class LeaveDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String sickLeave;
	
	private String sickLeaveUsed;
	
	private String annualLeave;
	
	private String annualLeaveUsed;
	
	private String otherLeave;
	
	private String otherLeaveUsed;

}
