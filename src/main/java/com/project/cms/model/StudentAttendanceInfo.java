package com.project.cms.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "student_attendance_info")
@Builder
public class StudentAttendanceInfo {
	
	@Id
	@SequenceGenerator(name = "student_attendance_info_sequence", sequenceName = "student_attendance_info_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "student_attendance_info_sequence")
	private Long id;
	
	private String jan;
	
	private String feb;
	
	private String mar;
	
	private String apr;
	
	private String may;
	
	private String june;
	
	@OneToOne(targetEntity = Student.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="fk_student_id", referencedColumnName = "id")
	private Student student;

}
