package com.coe.chat.domain;

import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = 5621530949083046332L;
	private String firstName;
	private String lastName;
	private String gender;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
