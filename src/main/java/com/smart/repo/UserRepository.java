package com.smart.repo;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Contact;
import com.smart.entities.Family;
import com.smart.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("select u from User u where u.email=:email")
	public User getUserByUserName(@Param("email") String email);

	
	
	public User save(@Valid Family family);
	@Query("select u from User u where u.id=:id")
	public User findByID(int id);



	public void save(Contact contact);

	

}
