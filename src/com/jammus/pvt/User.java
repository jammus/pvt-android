package com.jammus.pvt;

public class User {

	private final int id;
	private final String username;
	
	public User(int id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public int id() {
		return id;
	}
	
	public String username() {
		return username;
	}
	
}