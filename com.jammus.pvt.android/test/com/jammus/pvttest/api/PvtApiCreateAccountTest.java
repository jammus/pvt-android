package com.jammus.pvttest.api;

import static org.junit.Assert.*;

import java.util.Dictionary;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.*;

import com.jammus.pvt.api.ApiResponse;
import com.jammus.pvt.api.ApiTransportException;
import com.jammus.pvt.api.DuplicateUserException;

public class PvtApiCreateAccountTest extends PvtApiTestCase {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testPostsToUsersEndpoint() throws ApiTransportException {
		pvtApi.createAccount("Test User", "user@example.com", "password");
		verify(apiClient, times(1)).post(eq("/users"), any(Dictionary.class));
	}

	@Test
	public void testPostsNewUserDetails() throws ApiTransportException {
		pvtApi.createAccount("Test User", "user@example.com", "password");
		assertParameterEquals("Test User", "name");
		assertParameterEquals("user@example.com", "email");
		assertParameterEquals("password", "password");
	}
}