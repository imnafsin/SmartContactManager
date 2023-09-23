package com.smart.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Family;
import com.smart.entities.User;
import com.smart.entities.cFam;

public interface FamRepository extends JpaRepository<Family, Integer>{
	@Query("from Family as f where f.id=:userId")
	public List<Family>findFamilyDetailsByUser(@Param("userId")int userId);

	public void save(cFam fam1);

	public List<cFam> findFamilyDetailsByUser(User user);
	
	
}
 