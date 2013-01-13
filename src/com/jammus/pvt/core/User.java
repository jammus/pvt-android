package com.jammus.pvt.core;

public class User {

	private final int id;
	private final String email;
	private final String token;
	
	public User(int id, String email, String token) {
		this.id = id;
		this.email = email;
		this.token = token;
	}
	
	public int id() {
		return id;
	}
	
	public String email() {
		return email;
	}
	
	public String token() {
		return token;
	}
	
}