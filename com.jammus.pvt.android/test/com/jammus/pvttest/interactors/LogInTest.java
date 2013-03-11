package com.jammus.pvttest.interactors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jammus.pvt.api.ApiResponse;
import com.jammus.pvt.api.ApiTransportException;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.interactors.LogInResult;
import com.jammus.pvt.interactors.LogIn;

public class LogInTest {

	@Mock
	private PvtApi pvtApi;
	
	private LogIn logInUser;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		logInUser = new LogIn(pvtApi);
	}
	
	@Test
	public void testSendsCredentialsToServer() throws ApiTransportException {
		logInUser.execute("user@example.com", "hunter2");
		verify(pvtApi, times(1)).authenticateUser(eq("user@example.com"), eq("hunter2"));
	}
	
	@Test
	public void testResultIncludesIncorrectEmailPasswordOn401Response() throws ApiTransportException {
		when(pvtApi.authenticateUser(anyString(), anyString())).thenReturn(new ApiResponse(400, "Invalid deets"));
		LogInResult result = logInUser.execute("user@example.com", "hunter2");
		assertTrue(result.hasError(LogInResult.INVALID_EMAIL_OR_PASSWORD));
	}
	
	@Test
	public void testResultIncludesUnknownErrorOnUnsuccessfulResponse() throws ApiTransportException {
		when(pvtApi.authenticateUser(anyString(), anyString())).thenReturn(new ApiResponse(500, "Server error"));
		LogInResult result = logInUser.execute("user@example.com", "hunter2");
		assertTrue(result.hasError(LogInResult.UNKNOWN_ERROR));
	}
	
	@Test
	public void testResultIncludesUnknownErrorWhenApiTransportExceptionThrown() throws ApiTransportException {
		when(pvtApi.authenticateUser(anyString(), anyString())).thenThrow(new ApiTransportException(""));
		LogInResult result = logInUser.execute("user@example.com", "hunter2");
		assertTrue(result.hasError(LogInResult.UNKNOWN_ERROR));
	}
	
	@Test
	public void testResultIsOkOnSuccess() throws ApiTransportException {
		when(pvtApi.authenticateUser(anyString(), anyString())).thenReturn(new ApiResponse(200, "{ \"user\": { \"id\": 10001, \"name\": \"Test User\", \"email\": \"user@example.com\" }, \"access_token\": \"token\" } }"));
		LogInResult result = logInUser.execute("user@example.com", "hunter2");
		assertTrue(result.isOk());
	}
	
	@Test
	public void testResultIncludesUserOnSuccess() throws ApiTransportException {
		when(pvtApi.authenticateUser(anyString(), anyString())).thenReturn(new ApiResponse(200, "{ \"user\": { \"id\": 10001, \"name\": \"Test User\", \"email\": \"user@example.com\" }, \"access_token\": \"token\" } }"));
		LogInResult result = logInUser.execute("user@example.com", "hunter2");
		assertEquals(10001, result.user().id());
		assertEquals("Test User", result.user().name());
		assertEquals("user@example.com", result.user().email());
		assertEquals("token", result.user().token());
	}
	
	@Test
	public void testResultIncludesInvalidResponseOnJsonDecodeError() throws ApiTransportException {
		when(pvtApi.authenticateUser(anyString(), anyString())).thenReturn(new ApiResponse(200, "{ \"user\" }"));
		LogInResult result = logInUser.execute("user@example.com", "hunter2");
		assertTrue(result.hasError(LogInResult.INVALID_RESPONSE));
	}
	
}
