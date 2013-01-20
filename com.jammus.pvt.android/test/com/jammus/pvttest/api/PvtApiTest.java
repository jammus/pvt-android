package com.jammus.pvttest.api;

import static org.junit.Assert.*;

import java.util.Dictionary;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import com.jammus.pvt.api.ApiClient;
import com.jammus.pvt.api.ApiTransportException;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.core.PvtResult;

public class PvtApiTest {
	@Captor
	private ArgumentCaptor<Dictionary<String, String>> parameterCaptor;
	
	@Mock
	private ApiClient apiClient;
	
	private PvtApi pvtApi;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		pvtApi = new PvtApi(apiClient);
	}

	@Test
	public void testSubmitResultPostsToReportEndpoint() throws ApiTransportException {
		pvtApi.submitResult("", new PvtResult());
		verify(apiClient, times(1)).post(eq("/report"), any(Dictionary.class));
	}
	
	@Test
	public void testSubmitResultPostsAccessToken() throws ApiTransportException {
		pvtApi.submitResult("access_token", new PvtResult());
		verify(apiClient, times(1)).post(anyString(), parameterCaptor.capture());
		Dictionary<String, String> parameters = parameterCaptor.getValue();
		assertEquals("access_token", parameters.get("access_token"));
	}
}
