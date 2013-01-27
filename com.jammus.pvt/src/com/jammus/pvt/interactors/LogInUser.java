package com.jammus.pvt.interactors;

import org.json.JSONException;

import com.jammus.pvt.api.ApiResponse;
import com.jammus.pvt.api.ApiTransportException;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.core.User;

public class LogInUser {
	
	private PvtApi pvtApi;

	public LogInUser(PvtApi pvtApi) {
		this.pvtApi = pvtApi;
	}

	public LogInResult execute(String email, String password) {
		ApiResponse response;
		
		try {
			response = pvtApi.authenticateUser(email, password);
		} catch (ApiTransportException e) {
			return new LogInResult(LogInResult.UNKNOWN_ERROR);
		}
		
		if (response != null && response.code() == 200) {
			String token = decodeToken(response);
			User user = new User(0, email, token);
			return new LogInResult(null, user);
		}
		
		if (response != null && response.code() == 401) {
			return new LogInResult(LogInResult.INVALID_EMAIL_OR_PASSWORD);
		} 
		
		return new LogInResult(LogInResult.UNKNOWN_ERROR);
	}
	
	private String decodeToken(ApiResponse response) {
		try {
			return response.json().getString("access_token");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
	
}