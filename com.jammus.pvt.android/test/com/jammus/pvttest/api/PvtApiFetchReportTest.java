package com.jammus.pvttest.api;

import static org.junit.Assert.*;

import java.util.Dictionary;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import com.jammus.pvt.api.ApiTransportException;

import static org.mockito.Mockito.*;

public class PvtApiFetchReportTest extends PvtApiTestCase {
	@Captor
	protected ArgumentCaptor<Dictionary<String, String>> parameterCaptor;

	@Test
	public void testMakesGetRequestToEndpoint() throws ApiTransportException {
		pvtApi.fetchReport("access_token", "/report/12345678");
		verify(apiClient, times(1)).get(eq("/report/12345678"), any(Dictionary.class));
	}

	@Test
	public void testSendsUpAccessToken() throws ApiTransportException {
		pvtApi.fetchReport("12312312321", "/report/12345678");
		verify(apiClient, times(1)).get(anyString(), parameterCaptor.capture());
		Dictionary<String, String> parameters = parameterCaptor.getValue();
		assertEquals("Bearer 12312312321", parameters.get("Authorization"));
	}

}
