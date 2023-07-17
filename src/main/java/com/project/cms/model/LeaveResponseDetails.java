package com.project.cms.model;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class LeaveResponseDetails {
	
	private String sickLeave;
	
	private String sickLeaveUsed;
	
	private String annualLeave;
	
	private String annualLeaveUsed;
	
	private String otherLeave;
	
	private String otherLeaveUsed;
	
}
