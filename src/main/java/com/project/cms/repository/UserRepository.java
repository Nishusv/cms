package com.project.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.project.cms.model.UserRegistration;

@Repository
@Component
public interface UserRepository extends JpaRepository<UserRegistration, Long> {
	
//	@Query("SELECT u FROM UserRegistration u where u.email = : email ")
	UserRegistration findByEmail(@Param("email") String email);
	
	

}
