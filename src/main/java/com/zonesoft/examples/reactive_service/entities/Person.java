package com.zonesoft.examples.reactive_service.entities;

import java.time.LocalDate;

public class Person {
	
	private Long id;
	private String firstname;
	private String lastname;
	private LocalDate dateOfBirth;
	private Gender gender;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirthString) {
		this.dateOfBirth = LocalDate.parse(dateOfBirthString);
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ID="); sb.append(id);
		sb.append(", FIRST-NAME=");sb.append(firstname);
		sb.append(", LAST-NAME=");sb.append(lastname);
		sb.append(", DATE-OF-BIRTH=");sb.append(dateOfBirth);
		sb.append(", GENDER=");sb.append(gender);
		return sb.toString();
	}

}
