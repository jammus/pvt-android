package com.jammus.pvt.interactors;

import java.util.Arrays;
import java.util.List;

import com.jammus.pvt.core.User;

public class SignUpResult extends Result {

	public static final int DUPLICATE_ACCOUNT = -30001;
	
	public static final int INVALID_DETAILS = -30002;
	
	private final User user;

	public SignUpResult() {
		this(null, null);
	}

	public SignUpResult(int error) {
		this(Arrays.asList(error), null);
	}
	
	public SignUpResult(List<Integer> errors, User user) {
		super(errors);
		this.user = user;
	}

	public User user() {
		return user;
	}

}
