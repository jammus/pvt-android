package com.jammus.pvttest.api;

import static org.junit.Assert.*;

import java.util.Dictionary;

import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import com.jammus.pvt.api.ApiClient;
import com.jammus.pvt.api.ApiTransportException;
import com.jammus.pvt.api.PvtApi;

public class PvtApiTestCase {
	@Captor
	protected ArgumentCaptor<Dictionary<String, String>> parameterCaptor;
	
	@Mock
	protected ApiClient apiClient;
	
	protected PvtApi pvtApi;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		pvtApi = new PvtApi(apiClient);
	}
	
	protected void assertParameterEquals(String expected, String parameterName) throws ApiTransportException {
		verify(apiClient, times(1)).post(anyString(), parameterCaptor.capture(), any(Dictionary.class));
		Dictionary<String, String> parameters = parameterCaptor.getValue();
		assertEquals(expected, parameters.get(parameterName));
	}
	
	protected void assertHeaderEquals(String expected, String headerName) throws ApiTransportException {
		verify(apiClient, times(1)).post(anyString(), any(Dictionary.class), parameterCaptor.capture());
		Dictionary<String, String> parameters = parameterCaptor.getValue();
		assertEquals(expected, parameters.get(headerName));
	}
}