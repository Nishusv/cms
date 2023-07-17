package com.project.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.cms.model.AttendanceInfo;
import com.project.cms.model.StudentAttendanceInfo;

@Repository
public interface StudentAttendanceInfoRepository extends JpaRepository<StudentAttendanceInfo, Long> {

	@Query(nativeQuery = true, value = "select * from student_attendance_info a where a.fk_student_id=:id")
	StudentAttendanceInfo findByStudent(Long id);


}
