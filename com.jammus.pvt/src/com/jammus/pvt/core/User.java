package com.jammus.pvt.core;

public class User {

	private final int id;
	private final String name;
	private final String email;
	private final String token;
	private final boolean completedSurvey;
	
	public User(int id, String name, String email, String token) {
		this(id, name, email, token, false);
	}

	public User(int id, String name, String email, String token, boolean completedSurvey) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.token = token;
		this.completedSurvey = completedSurvey;
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

	public boolean hasCompletedScreening() {
		return completedSurvey;
	}
	
}