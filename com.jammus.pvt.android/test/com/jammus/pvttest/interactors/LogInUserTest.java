package com.jammus.pvttest.interactors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jammus.pvt.api.ApiResponse;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.interactors.LogInResult;
import com.jammus.pvt.interactors.LogInUser;

public class LogInUserTest {

	@Mock
	private PvtApi pvtApi;
	
	private LogInUser logInUser;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		logInUser = new LogInUser(pvtApi);
	}
	
	@Test
	public void testSendsCredentialsToServer() {
		logInUser.execute("user@example.com", "hunter2");
		verify(pvtApi, times(1)).authenticateUser(eq("user@example.com"), eq("hunter2"));
	}
	
	@Test
	public void testResultIncludesIncorrectEmailPasswordOn401Response() {
		when(pvtApi.authenticateUser(anyString(), anyString())).thenReturn(new ApiResponse(401, "Invalid deets"));
		LogInResult result = logInUser.execute("user@example.com", "hunter2");
		assertTrue(result.hasError(LogInResult.INVALID_EMAIL_OR_PASSWORD));
	}
	
	@Test
	public void testResultIncludesUnknownErrorOnUnsuccessfulResponse() {
		when(pvtApi.authenticateUser(anyString(), anyString())).thenReturn(new ApiResponse(500, "Server error"));
		LogInResult result = logInUser.execute("user@example.com", "hunter2");
		assertTrue(result.hasError(LogInResult.UNKNOWN_ERROR));
	}
	
	@Test
	public void testResultIsOkOnSuccess() {
		when(pvtApi.authenticateUser(anyString(), anyString())).thenReturn(new ApiResponse(200, ""));
		LogInResult result = logInUser.execute("user@example.com", "hunter2");
		assertTrue(result.isOk());
	}
	
	@Test
	public void testResultIncludesAccessTokenOnSuccess() {
		when(pvtApi.authenticateUser(anyString(), anyString())).thenReturn(new ApiResponse(200, "{ \"access_token\": \"token\" }"));
		LogInResult result = logInUser.execute("user@example.com", "hunter2");
		assertEquals("token", result.user().token());
	}
	
}
