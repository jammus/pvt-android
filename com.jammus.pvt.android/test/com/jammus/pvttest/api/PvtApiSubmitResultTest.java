package com.jammus.pvttest.api;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;

import org.junit.Test;

import static org.mockito.Mockito.*;

import com.jammus.pvt.api.ApiTransportException;
import com.jammus.pvt.core.PvtResult;

public class PvtApiSubmitResultTest extends PvtApiTestCase {

	@Test
	public void testPostsToReportEndpoint() throws ApiTransportException {
		pvtApi.submitResult("", new PvtResult());
		verify(apiClient, times(1)).post(eq("/report"), any(Dictionary.class), any(Dictionary.class));
	}
	
	@Test
	public void testPostsAccessToken() throws ApiTransportException {
		pvtApi.submitResult("access_token", new PvtResult());
		assertParameterEquals("access_token", "access_token");
	}
	
	@Test
	public void testPostsTimestampAsSeconds() throws ApiTransportException, ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2013-01-20");
		PvtResult pvtResult = new PvtResult(date, new float[] { }, 0);
		pvtApi.submitResult("access_token", pvtResult);
		assertParameterEquals("1358640000", "timestamp");
	}
	
	@Test
	public void testPostsErrorCount() throws ApiTransportException {
		PvtResult pvtResult = new PvtResult(new Date(), new float[] { }, 9);
		pvtApi.submitResult("access_token", pvtResult);
		assertParameterEquals("9", "errors");
	}
	
	@Test
	public void testPostsConcatenatedResponseTimes() throws ApiTransportException {
		PvtResult pvtResult = new PvtResult(new Date(), new float[] { 123.567f, 567.876f, 300f }, 0);
		pvtApi.submitResult("access_token", pvtResult);
		assertParameterEquals("123.567,567.876,300.0", "response_times");
	}
}