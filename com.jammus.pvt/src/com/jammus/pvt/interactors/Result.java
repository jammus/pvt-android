package com.jammus.pvt.interactors;

import java.util.ArrayList;
import java.util.List;

public class Result {
	
	public static final int UNKNOWN_ERROR = -10001;
	
	private final List<Integer> errors;

	public Result(int error) {
		List<Integer> errors = new ArrayList<Integer>();
        errors.add(error);
        this.errors = errors;
	}

	public Result() {
		this(new ArrayList<Integer>());
	}

	public Result(List<Integer> errors) {
		this.errors = errors;
	}

	public boolean isOk() {
		return errors == null || errors.size() == 0;
	}

	public boolean hasError(int error) {
		if (errors == null) {
			return false;
		}
		
		for (int x: errors) {
			if (x == error) {
				return true;
			}
		}
		return false;
	}

}
