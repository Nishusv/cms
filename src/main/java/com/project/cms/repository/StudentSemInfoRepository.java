package com.project.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.cms.model.AttendanceInfo;
import com.project.cms.model.SemInfo;
import com.project.cms.model.StudentAttendanceInfo;

@Repository
public interface StudentSemInfoRepository extends JpaRepository<SemInfo, Long> {

	@Query(nativeQuery = true, value = "select * from sem_info a where a.fk_student_id=:id")
	SemInfo findByStudent(Long id);


}
