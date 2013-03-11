package com.jammus.pvttest.api;

import static org.junit.Assert.*;

import java.util.Dictionary;

import org.junit.Test;

import static org.mockito.Mockito.*;

import com.jammus.pvt.api.ApiTransportException;

public class PvtApiSignUpTest extends PvtApiTestCase {
	
	@Test
	public void testPostsToUsersEndpoint() throws ApiTransportException {
		pvtApi.signUp("Test User", "user@example.com", "password");
		verify(apiClient, times(1)).post(eq("/users"), any(Dictionary.class), any(Dictionary.class));
	}

	@Test
	public void testPostsNewUserDetails() throws ApiTransportException {
		pvtApi.signUp("Test User", "user@example.com", "password");
		assertParameterEquals("Test User", "name");
		assertParameterEquals("user@example.com", "email");
		assertParameterEquals("password", "password");
	}
	
}