package com.jammus.pvt.core;

public class User {

	private final int id;
	private final String name;
	private final String email;
	private final String token;
	
	public User(int id, String name, String email, String token) {
		this.id = id;
		this.name = name;
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

	public String name() {
		return name;
	}
	
}