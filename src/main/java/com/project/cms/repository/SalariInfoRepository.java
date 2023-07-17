package com.project.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.project.cms.model.SalaryInfo;

@Repository
@Component
public interface SalariInfoRepository extends JpaRepository<SalaryInfo, Long> {
	
	@Query(nativeQuery = true, value = "select * from salary_info a where a.fk_teacher_id=:id")
	List<SalaryInfo> findByTeacher(Long id);


}
