package com.smart.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cmd")
public class cFam {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private int age;
	
	private String gender;
	
	private String country;
	
	private String state;
	 
	
	private String city;
	
	private String relation;
	
	private String bloodGroup;

	@ManyToOne
	private Contact contact;

	
	public cFam() {
		// TODO Auto-generated constructor stub
	}


	

	public cFam(String name, int age, String gender, String country, String state, String city, String relation,
			String bloodGroup, Contact contact ) {
		
	
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.country = country;
		this.state = state;
		this.city = city;
		this.relation = relation;
		this.bloodGroup = bloodGroup;
		this.contact = contact;
		
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getRelation() {
		return relation;
	}


	public void setRelation(String relation) {
		this.relation = relation;
	}


	public String getBloodGroup() {
		return bloodGroup;
	}


	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}


	public Contact getContact() {
		return contact;
	}
	
	public void setContact(Contact contact) {
		this.contact = contact;
	}








	}
