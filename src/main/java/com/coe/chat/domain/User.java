package com.coe.chat.domain;

import java.io.Serializable;

public class User extends Person implements Serializable {
	private static final long serialVersionUID = 6845125473416397513L;
	private String username;
	private boolean available;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}
