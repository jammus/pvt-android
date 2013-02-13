package com.jammus.pvt.api;

import org.json.JSONException;
import org.json.JSONObject;

import com.jammus.pvt.core.User;

public class ApiResponseUserTransformer {
	
	public static User transform(ApiResponse response) {
		try {
			JSONObject jsonResponse = response.toJson();
			JSONObject jsonUser = jsonResponse.getJSONObject("user");
			
			return new User(
				jsonUser.getInt("id"),
				jsonUser.getString("name"),
				jsonUser.getString("email"),
				jsonResponse.getString("access_token")
			);
		} catch (JSONException e) {
			return null;
		}
	}
	
}
