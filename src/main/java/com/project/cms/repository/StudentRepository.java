package com.project.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cms.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	Student findByEmail(String email);

}
