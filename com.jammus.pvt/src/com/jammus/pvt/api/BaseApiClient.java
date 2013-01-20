package com.jammus.pvt.api;

import java.util.Dictionary;
import java.util.Hashtable;

import com.jammus.pvt.core.PvtResult;

public abstract class BaseApiClient implements ApiClient {
	
	protected static final String BASE_URL = "http://pvt-api.eu01.aws.af.cm";

	public String submitResult(String accessToken, PvtResult result) throws ApiTransportException {
		Dictionary<String, String> postParams = new Hashtable<String, String>();
		
		postParams.put("access_token", accessToken);
		postParams.put("timestamp", String.valueOf(result.date().getTime() / 1000));
		postParams.put("errors", String.valueOf(result.errorCount()));
		postParams.put("response_times", responseTimesToString(result.responseTimes()));
		
		return post("/report", postParams).message();
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
	
	abstract protected ApiResponse post(String url, Dictionary<String, String> parameters) throws ApiTransportException;
}
