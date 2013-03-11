package com.jammus.pvt.api;

import java.util.Dictionary;
import java.util.Hashtable;

import com.jammus.pvt.core.PvtResult;

public class PvtApi {
	
	private ApiClient apiClient;
	
	public PvtApi(ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	public ApiResponse submitResult(String accessToken, PvtResult result) throws ApiTransportException {
		Dictionary<String, String> parameters = new Hashtable<String, String>();
		parameters.put("access_token", accessToken);
		parameters.put("timestamp", String.valueOf(result.date().getTime() / 1000));
		parameters.put("errors", String.valueOf(result.errorCount()));
		parameters.put("response_times", responseTimesToString(result.responseTimes()));
		
		return apiClient.post("/report", parameters, new Hashtable<String, String>());
	}
	
	private String responseTimesToString(float[] times) {
		StringBuilder sb = new StringBuilder();
		
		for (int index = 0; index < times.length; index++) {
			if (index > 0) {
				sb.append(",");
			}
			sb.append(String.valueOf(times[index]));
		}
		
		return sb.toString();
	}

	public ApiResponse signUp(String name, String email, String password) throws ApiTransportException {
		Dictionary<String, String> parameters = new Hashtable<String, String>();
		parameters.put("name", name);
		parameters.put("email", email);
		parameters.put("password", password);
		
		return apiClient.post("/users", parameters, new Hashtable<String, String>());
	}

	public ApiResponse authenticateUser(String email, String password) throws ApiTransportException {
		Dictionary<String, String> parameters = new Hashtable<String, String>();
		parameters.put("username", email);
		parameters.put("password", password);
		
		parameters.put("grant_type", "password");
		parameters.put("client_id", "android");
		
		return apiClient.post("/token", parameters, new Hashtable<String, String>());
	}

	public ApiResponse fetchReport(String accessToken, String url) throws ApiTransportException {
		Dictionary<String, String> headers = new Hashtable<String, String>();
		headers.put("Authorization", "Bearer " + accessToken);
		return apiClient.get(url, headers);
	}
	
}
