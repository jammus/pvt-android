package com.jammus.pvttest.api;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jammus.pvt.api.ApiTransportException;

import static org.mockito.Mockito.*;

public class PvtApiFetchReportTest extends PvtApiTestCase {

	@Test
	public void testMakesGetRequestToEndpoint() throws ApiTransportException {
		pvtApi.fetchReport("/report/12345678");
		verify(apiClient, times(1)).get(eq("/report/12345678"));
	}

}
