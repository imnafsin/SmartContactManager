package com.smart.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Family {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@NotBlank
	private String name;
	
	private String gender;
	
	private int age;
	
	private String Country;
	
	private String state;
	
	private String city;
	
	private String relation;
	
	
	@JsonBackReference
	@ManyToOne
	@JsonIgnore

	private User user;

	public Family() {
		super();
			}

	public Family(int id, @NotBlank String name, @NotBlank String gender, @NotBlank int age, String country,
			String state, String city, @NotBlank String relation, User user) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.Country = country;
		this.state = state;
		this.city = city;
		this.relation = relation;
		this.user = user;
	}
	public Family( String name,  String gender, int age, String country,
			String state, String city,  String relation, User user) {
		
	
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.Country = country;
		this.state = state;
		this.city = city;
		this.relation = relation;
		this.user = user;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Family [id=" + id + ", name=" + name + ", gender=" + gender + ", age=" + age + ", Country=" + Country
				+ ", state=" + state + ", city=" + city + ", relation=" + relation + ", user=" + user + "]";
	}
	
	
	
}
