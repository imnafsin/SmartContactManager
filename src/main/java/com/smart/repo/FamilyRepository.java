package com.smart.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Family;
import com.smart.entities.User;
import com.smart.entities.cFam;

public interface FamilyRepository extends JpaRepository<User, Integer> {
	
	  @Query("select u from User u where u.email=:email") public User
	  getUserByUserName(@Param("email") String email);
	 
	public void save(Family family);
	
	@Query("from Family as f where f.user.id=:userId")
	public Family getFamilyByUser(@Param("userId")int userId);
	
	
	public List<Family>findByFamilies(int i,Pageable pageable);

	public void save(cFam cfam);
	

//@Query("select f from User f where f.name=:name")
//	public Family getFamily(@Param("name" String name));
	
}
