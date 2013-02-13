package com.jammus.pvt.interactors;

import com.jammus.pvt.api.ApiResponse;
import com.jammus.pvt.api.ApiResponseUserTransformer;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.core.User;

public class SignUp {
	
	private PvtApi pvtApi;

	public SignUp(PvtApi pvtApi) {
		this.pvtApi = pvtApi;
	}

	public SignUpResult execute(String name, String email, String password) {
		ApiResponse response = pvtApi.signUp(name, email, password);
		
		if (response != null && response.code() == 409) {
			return new SignUpResult(SignUpResult.DUPLICATE_ACCOUNT);
		}
		
		if (response != null && response.code() == 400) {
			return new SignUpResult(SignUpResult.INVALID_DETAILS);
		}
		
		if (response != null && response.code() == 200) {
			User user = ApiResponseUserTransformer.transform(response);
			return new SignUpResult(null, user);
		}
		
		return new SignUpResult(SignUpResult.UNKNOWN_ERROR);
	}

}
