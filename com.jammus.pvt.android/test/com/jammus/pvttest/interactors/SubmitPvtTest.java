package com.jammus.pvttest.interactors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.Before;
import org.junit.Test;

import com.jammus.pvt.api.ApiResponse;
import com.jammus.pvt.api.ApiTransportException;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.core.PvtReport;
import com.jammus.pvt.core.PvtResult;
import com.jammus.pvt.core.User;
import com.jammus.pvt.interactors.SubmitPvt;
import com.jammus.pvt.interactors.SubmitPvtResult;

public class SubmitPvtTest {
	
	@Mock
	private PvtApi pvtApi;
	
	private SubmitPvt submitPvt;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		submitPvt = new SubmitPvt(pvtApi);
	}

	@Test
	public void testSendsAccessTokenToServer() throws ApiTransportException {
		User user = new User(0, "Test User", "user@example.com", "access_token");
		submitPvt.execute(user, new PvtResult());
		verify(pvtApi, times(1)).submitResult(eq("access_token"), any(PvtResult.class));
	}
	
	@Test
	public void testSendsPvtResultToServer() throws ApiTransportException {
		PvtResult pvtResult = new PvtResult();
		submitPvt.execute(new User(0, "", "", ""), pvtResult);
		verify(pvtApi, times(1)).submitResult(anyString(), eq(pvtResult));
	}
	
	@Test
	public void testIncludesNotAuthorisedOn401Response() throws ApiTransportException {
		when(pvtApi.submitResult(anyString(), any(PvtResult.class))).thenReturn(new ApiResponse(401, ""));
		SubmitPvtResult result = submitPvt.execute(new User(0, "", "", ""), new PvtResult());
		assertTrue(result.hasError(SubmitPvtResult.NOT_AUTHORISED));
	}
	
	@Test
	public void testIncludesUnknownErrorWhenSubmissionThrows() throws ApiTransportException
	{
		when(pvtApi.submitResult(anyString(), any(PvtResult.class))).thenThrow(new ApiTransportException(""));
		SubmitPvtResult result = submitPvt.execute(new User(0, "", "", ""), new PvtResult());
		assertTrue(result.hasError(SubmitPvtResult.UNKNOWN_ERROR));
	}

	@Test
	public void testLoadsReportOn201Response() throws ApiTransportException
	{
		when(pvtApi.submitResult(anyString(), any(PvtResult.class))).thenReturn(
			new ApiResponse(
				201,
				"{\"response\": { \"location\": \"http://path/to/report\" } }"
			)
		);
		SubmitPvtResult result = submitPvt.execute(new User(0, "", "", ""), new PvtResult());
		verify(pvtApi, times(1)).fetchReport(eq("http://path/to/report"));
	}
	
	@Test
	public void testLoadsReportOn301Response() throws ApiTransportException
	{
		when(pvtApi.submitResult(anyString(), any(PvtResult.class))).thenReturn(
			new ApiResponse(
				301,
				"{\"response\": { \"location\": \"http://path/to/report\" } }"
			)
		);
		SubmitPvtResult result = submitPvt.execute(new User(0, "", "", ""), new PvtResult());
		verify(pvtApi, times(1)).fetchReport(eq("http://path/to/report"));
	}
	
	@Test
	public void testIncludesUnknownErrorWhenNoReportUrlIncluded() throws ApiTransportException
	{
		when(pvtApi.submitResult(anyString(), any(PvtResult.class))).thenReturn(
			new ApiResponse(
				201,
				"{\"response\": { } }"
			)
		);
		SubmitPvtResult result = submitPvt.execute(new User(0, "", "", ""), new PvtResult());
		assertTrue(result.hasError(SubmitPvtResult.UNABLE_TO_LOAD_REPORT));
	}
	
	@Test
	public void testIncludesCouldNotLoadReportWhenFetchReportThrows() throws ApiTransportException
	{
		whenSubmissionIsSuccessful();
		when(pvtApi.fetchReport(anyString())).thenThrow(new ApiTransportException(""));
		SubmitPvtResult result = submitPvt.execute(new User(0, "", "", ""), new PvtResult());
		assertTrue(result.hasError(SubmitPvtResult.UNABLE_TO_LOAD_REPORT));
	}
	
	@Test
	public void testResultIsOkWhenReportIsLoaded() throws ApiTransportException
	{
		whenSubmissionIsSuccessful();
		when(pvtApi.fetchReport(anyString())).thenReturn(
			new ApiResponse(
				200,
				"{" +
					"\"response\":" +
					"{" +
						"\"report\":" +
							"{" +
								"timestamp: 1361725628," +
								"errors: 2," +
								"lapses: 1," +
								"average_response_time: 234.45," +
							"}" +
					"}" +
				"}"
			)
		);
		SubmitPvtResult result = submitPvt.execute(new User(0, "", "", ""), new PvtResult());
		assertTrue(result.isOk());
	}
	
	@Test
	public void testResultIncludeReportWhenLoaded() throws ApiTransportException
	{
		whenSubmissionIsSuccessful();
		when(pvtApi.fetchReport(anyString())).thenReturn(
			new ApiResponse(
				200,
				"{" +
					"\"response\":" +
					"{" +
						"\"report\":" +
							"{" +
								"timestamp: 1361725628," +
								"errors: 2," +
								"lapses: 1," +
								"average_response_time: 234.45," +
							"}" +
					"}" +
				"}"
			)
		);
		SubmitPvtResult result = submitPvt.execute(new User(0, "", "", ""), new PvtResult());
		
		PvtReport pvtReport = result.report();
		assertEquals(2, pvtReport.errors());
		assertEquals(1, pvtReport.lapses());
		assertEquals(234.45, pvtReport.averageRt(), 0.001f);
		assertEquals(1361725628000l, pvtReport.date().getTime());
	}
	
	private void whenSubmissionIsSuccessful() throws ApiTransportException
	{
		when(pvtApi.submitResult(anyString(), any(PvtResult.class))).thenReturn(
			new ApiResponse(
				201,
				"{\"response\": { \"location\": \"http://path/to/report\" } }"
			)
		);
	}
}
