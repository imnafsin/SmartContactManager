package com.smart.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.entities.cFam;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

	// Pagination
	@Query("from Contact as c where c.user.id=:userId")
	/// Showing contacts page by page
	public Page<Contact> findContactsByUser(@Param("userId") int userId, Pageable pepagable);

	public List<Contact> findByNameContainingAndUser(String name, User user);

	@Query("select u from Contact u where u.email=:name")
	public Contact getContactByContactName(@Param("name") String name);

	@Query("select u from Contact u where u.cId=:cId")
	public Contact findByContactId(@Param("cId") int cId);
	
	
	@Query("select u from Contact u where u.cId=:cId")
	public Integer getcId(@Param("cId") Integer cId);
	
	
	@Query("select u from Contact u where u.cId=:cId")
	public Contact getContactByContactId(@Param("cId") Integer cId);

	

	//public cFam getFamilyDetailsByContactId(String cId);
	
	
		//public List<cFam>getFamilyDetailsCFam(String name,Contact contact);

	/*
	 * @Query("select u from Contact u where u.id=:cId") public Contact
	 * getByID(@Param("cId")Contact contact);
	 */

}
