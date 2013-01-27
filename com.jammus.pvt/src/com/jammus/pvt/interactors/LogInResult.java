package com.jammus.pvt.interactors;

import java.util.Arrays;
import java.util.List;

import com.jammus.pvt.core.User;

public class LogInResult extends Result {
	
	private final User user;

	public LogInResult() {
		this(null, null);
	}

	public LogInResult(int error) {
		this(Arrays.asList(error), null);
	}
	
	public LogInResult(List<Integer> errors, User user) {
		super(errors);
		this.user = user;
	}

	public static final int INVALID_EMAIL_OR_PASSWORD = -20001;

	public User user() {
		return user;
	}

}
