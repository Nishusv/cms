package com.project.cms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cms.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

	Teacher findByEmail(String email);

}
