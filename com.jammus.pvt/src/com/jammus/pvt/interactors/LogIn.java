package com.jammus.pvt.interactors;

import com.jammus.pvt.api.ApiResponse;
import com.jammus.pvt.api.ApiResponseUserTransformer;
import com.jammus.pvt.api.ApiTransportException;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.core.User;

public class LogIn {
	
	private PvtApi pvtApi;

	public LogIn(PvtApi pvtApi) {
		this.pvtApi = pvtApi;
	}

	public LogInResult execute(String email, String password) {
		ApiResponse response;
		
		try {
			response = pvtApi.authenticateUser(email, password);
		} catch (ApiTransportException e) {
			return new LogInResult(LogInResult.UNKNOWN_ERROR);
		}
		
		if (response != null && response.code() == 400) {
			return new LogInResult(LogInResult.INVALID_EMAIL_OR_PASSWORD);
		} 
		
		if (response != null && response.code() == 200) {
			return decodeResponse(response);
		}
		
		return new LogInResult(LogInResult.UNKNOWN_ERROR);
	}
	
	private LogInResult decodeResponse(ApiResponse response) {
		User user = ApiResponseUserTransformer.transform(response);
		
		if (user == null) {
			return new LogInResult(LogInResult.INVALID_RESPONSE);
		}
		
		return new LogInResult(null, user);
	}
	
}