package com.jammus.pvt.android.data;

import com.jammus.pvt.api.ApiClient;
import com.jammus.pvt.api.ApiTransportException;
import com.jammus.pvt.core.PvtResult;

public class PvtResultsSubmission {
	private ApiClient client;
	
	public PvtResultsSubmission(ApiClient client) {
		this.client = client;
	}
	
	public String submit(String accessToken, PvtResult result) {
		try {
			return client.submitResult(accessToken, result);
		} catch (ApiTransportException e) {
			return e.getMessage();
		}
	}
}
