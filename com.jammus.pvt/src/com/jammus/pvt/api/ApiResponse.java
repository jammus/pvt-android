package com.jammus.pvt.api;

import org.json.JSONException;
import org.json.JSONObject;

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

	public JSONObject json() {
		try {
			return new JSONObject(message);
		} catch (JSONException e) {
			return new JSONObject();
		}
	}
}
