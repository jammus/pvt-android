package com.jammus.pvt.interactors;

import java.sql.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.jammus.pvt.api.ApiResponse;
import com.jammus.pvt.api.ApiTransportException;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.core.PvtReport;
import com.jammus.pvt.core.PvtResult;
import com.jammus.pvt.core.User;

public class SubmitPvt {
	
	private PvtApi pvtApi;

	public SubmitPvt(PvtApi pvtApi) {
		this.pvtApi = pvtApi;
	}

	public SubmitPvtResult execute(User user, PvtResult pvtResult) {
		ApiResponse response;
		
		try {
			response = pvtApi.submitResult(user.token(), pvtResult);
		} catch (ApiTransportException e) {
			response = null;
		}
		
		if (response == null) {
			return new SubmitPvtResult(SubmitPvtResult.UNKNOWN_ERROR);
		}
		
		if (response.code() == 401) {
			return new SubmitPvtResult(SubmitPvtResult.NOT_AUTHORISED);
		}
		
		String reportUrl;
		try {
			reportUrl = decodeUrl(response);
		} catch (JSONException e) {
			return new SubmitPvtResult(SubmitPvtResult.UNABLE_TO_LOAD_REPORT);
		}
		
		return loadReport(reportUrl);
	}
	
	private String decodeUrl(ApiResponse response) throws JSONException
	{
		JSONObject json = response.toJson();
		JSONObject jsonData;
		jsonData = json.getJSONObject("response");
		return jsonData.getString("location");
	}
	
	private SubmitPvtResult loadReport(String reportUrl)
	{
		ApiResponse response;
		try {
			response = pvtApi.fetchReport(reportUrl);
		} catch (ApiTransportException e) {
			response = null;
		}
		
		if (response == null) {
			return new SubmitPvtResult(SubmitPvtResult.UNABLE_TO_LOAD_REPORT);
		}
		
		PvtReport report;
		try {
			report = decodeReport(response);
		} catch (JSONException e) {
			return new SubmitPvtResult(SubmitPvtResult.UNABLE_TO_LOAD_REPORT);
		}
		
		return new SubmitPvtResult(null, report);
	}

	private PvtReport decodeReport(ApiResponse response) throws JSONException {
		JSONObject jsonReport = response.toJson()
				.getJSONObject("response")
				.getJSONObject("report");
		
		return new PvtReport(
				new Date(jsonReport.getLong("timestamp") * 1000),
				jsonReport.getInt("errors"),
				jsonReport.getInt("lapses"),
				(float) jsonReport.getDouble("average_response_time")
		);
	}

}
