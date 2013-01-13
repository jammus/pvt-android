package com.jammus.pvt.api;

public class ApiResponse {
	private final int code;
	private final String message;
	
	public ApiResponse (int code, String message) {
		this.code = code;
		this.message = message;
	}

	public String message() {
		return message;
	}
	
	public int code() {
		return code;
	}
}
