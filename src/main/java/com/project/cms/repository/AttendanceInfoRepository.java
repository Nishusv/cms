package com.project.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.cms.model.AttendanceInfo;

@Repository
public interface AttendanceInfoRepository extends JpaRepository<AttendanceInfo, Long> {

	@Query(nativeQuery = true, value = "select * from attendance_info a where a.fk_teacher_id=:id")
	AttendanceInfo findByTeacher(Long id);


}
