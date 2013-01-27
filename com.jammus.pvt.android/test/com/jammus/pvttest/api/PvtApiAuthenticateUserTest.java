package com.jammus.pvttest.api;

import static org.junit.Assert.*;

import java.util.Dictionary;

import org.junit.Test;

import static org.mockito.Mockito.*;

import com.jammus.pvt.api.ApiTransportException;

public class PvtApiAuthenticateUserTest extends PvtApiTestCase {

	@Test
	public void testPostsToLoginEndpoint() throws ApiTransportException {
		pvtApi.authenticateUser("user@example.com", "hunter2");
		verify(apiClient, times(1)).post(eq("/login"), any(Dictionary.class));
	}
	
	@Test
	public void testPostsEmailAndPassword() throws ApiTransportException {
		pvtApi.authenticateUser("user@example.com", "hunter2");
		assertParameterEquals("user@example.com", "email");
		assertParameterEquals("hunter2", "password");
	}

}
