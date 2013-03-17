package com.jammus.pvt.api;

import org.json.JSONException;
import org.json.JSONObject;

import com.jammus.pvt.core.User;

public class ApiResponseUserTransformer {
	
	public static User transform(ApiResponse response) {
		JSONObject jsonResponse, jsonUser;
		int id;
		String name, email, access_token;
		boolean surveyCompleted;
		
		try {
			jsonResponse = response.toJson();
			jsonUser = jsonResponse.getJSONObject("user");
			id = jsonUser.getInt("id");
			name = jsonUser.getString("name");
			email = jsonUser.getString("email");
		}
		catch (JSONException e) {
			return null;
		}
		
		try {
			access_token = jsonResponse.getString("access_token");
		} catch (JSONException e) {
			access_token = null;
		}
		
		try {
			surveyCompleted = jsonUser.getBoolean("survey_completed");
		} catch (JSONException e) {
			surveyCompleted = false;
		}
			
		return new User(
			id,
			name,
			email,
			access_token,
			surveyCompleted
		);
	}
	
}
