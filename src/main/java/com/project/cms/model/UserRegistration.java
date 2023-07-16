package com.project.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="user_regitration", uniqueConstraints = @UniqueConstraint(
		columnNames = "email"))
public class UserRegistration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "user_registration_sequence", sequenceName = "user_registration_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "user_registration_sequence")
	private Long id;

	private String fullName;

	@Column(name = "email", nullable = false)
	private String email;

	private String password;

	private Boolean isActive;

	private String authorization;
	
	private String role;

}
