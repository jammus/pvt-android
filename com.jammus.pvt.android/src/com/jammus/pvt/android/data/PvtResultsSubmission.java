package com.jammus.pvt.android.data;

import com.jammus.pvt.api.ApiTransportException;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.core.PvtResult;

public class PvtResultsSubmission {
	private PvtApi pvtApi;
	
	public PvtResultsSubmission(PvtApi pvtApi) {
		this.pvtApi = pvtApi;
	}
	
	public String submit(String accessToken, PvtResult result) {
		try {
			return pvtApi.submitResult(accessToken, result);
		} catch (ApiTransportException e) {
			return e.getMessage();
		}
	}
}
