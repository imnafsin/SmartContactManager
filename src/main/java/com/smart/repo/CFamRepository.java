package com.smart.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Contact;
import com.smart.entities.cFam;

public interface CFamRepository extends JpaRepository<cFam, Integer> {

	@Query("from cFam as f where f.id=:id")
	public List<cFam> findFamilyDetailsByUser(@Param("id") int id);

	void save(Contact contact);

	@Query("from cFam as f where f.id=:fid")
	public List<cFam> findFamilyDetails(@Param("fid") int fid);
	
	
	  @Query("from Contact as c where c.cId=:contactId") public List<cFam>
	  findcFamilyDetailsByContact(@Param("contactId")int contactId);
	 
	//public List<cFam>
	//public void save(List<cFam> cfam);	
	
	

}
