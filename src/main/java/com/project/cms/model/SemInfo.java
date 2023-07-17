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
@Table(name = "sem_info")
@Builder
public class SemInfo {
	
	@Id
	@SequenceGenerator(name = "sem_info_sequence", sequenceName = "sem_info_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "sem_info_sequence")
	private Long id;
	
	private String sem1;
	
	private String sem2;
	
	private String sem3;
	
	private String sem4;
	
	private String sem5;
	
	private String sem6;
	
	@OneToOne(targetEntity = Student.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="fk_student_id", referencedColumnName = "id")
	private Student student;

}
