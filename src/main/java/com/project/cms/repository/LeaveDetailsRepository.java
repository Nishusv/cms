package com.project.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.project.cms.model.LeaveDetails;

@Repository
@Component
public interface LeaveDetailsRepository extends JpaRepository<LeaveDetails, Long> {
	
	@Query(nativeQuery = true, value = "select * from leave_details a where a.fk_teacher_id=:id")
	LeaveDetails findByTeacher(Long id);
	
}
