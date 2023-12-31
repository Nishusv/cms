package com.project.cms.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
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
	@SequenceGenerator(name = "leave_details_sequence", sequenceName = "leave_details_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "leave_details_sequence")
	private Long id;
	
	private String sickLeave;
	
	private String sickLeaveUsed;
	
	private String annualLeave;
	
	private String annualLeaveUsed;
	
	private String otherLeave;
	
	private String otherLeaveUsed;
	
	@OneToOne(targetEntity = Teacher.class, cascade = CascadeType.ALL)
	@JoinColumn(name="fk_teacher_id", referencedColumnName = "id")
	private Teacher teacher;

}
