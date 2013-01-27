package com.jammus.pvt.interactors;

import com.jammus.pvt.api.ApiResponse;
import com.jammus.pvt.api.PvtApi;

public class LogInUser {
	
	private PvtApi pvtApi;

	public LogInUser(PvtApi pvtApi) {
		this.pvtApi = pvtApi;
	}

	public LogInResult execute(String email, String password) {
		ApiResponse response = pvtApi.authenticateUser(email, password);
		
		if (response != null && response.code() == 200) {
			return new LogInResult();
		}
		
		if (response != null && response.code() == 401) {
			return new LogInResult(LogInResult.INVALID_EMAIL_OR_PASSWORD);
		} 
		
		return new LogInResult(LogInResult.UNKNOWN_ERROR);
	}
	
}