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
import com.jammus.pvt.core.User;
import com.jammus.pvt.interactors.SignUp;
import com.jammus.pvt.interactors.SignUpResult;

public class SignUpTest {

	@Mock
	private PvtApi pvtApi;
	
	private SignUp signUp;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		signUp = new SignUp(pvtApi);
	}
	
	@Test
	public void testSendsDetailsToServer() throws ApiTransportException {
		signUp.execute("Test User", "user@example.com", "hunter2");
		verify(pvtApi, times(1)).signUp(eq("Test User"), eq("user@example.com"), eq("hunter2"));
	}

	@Test
	public void testResultIncludesDuplicateAccountOn409Response() throws ApiTransportException {
		when(pvtApi.signUp(anyString(), anyString(), anyString())).thenReturn(new ApiResponse(409, "You already sign up"));
		SignUpResult result = signUp.execute("Test User", "user@example.com", "hunter2");
		assertTrue(result.hasError(SignUpResult.DUPLICATE_ACCOUNT));
	}
	
	@Test
	public void testResultIncludesInvalidDetailsOn400Response() throws ApiTransportException {
		when(pvtApi.signUp(anyString(), anyString(), anyString())).thenReturn(new ApiResponse(400, "Invalid details"));
		SignUpResult result = signUp.execute("Test User", "userexample.com", "");
		assertTrue(result.hasError(SignUpResult.INVALID_DETAILS));
	}
	
	@Test
	public void testResultIncludesUnknownErrorOn500Response() throws ApiTransportException {
		when(pvtApi.signUp(anyString(), anyString(), anyString())).thenReturn(new ApiResponse(500, "Ruh-roh"));
		SignUpResult result = signUp.execute("Test User", "user@example.com", "hunter2");
		assertTrue(result.hasError(SignUpResult.UNKNOWN_ERROR));
	}
	
	@Test
	public void testResultIncludesUnknownErrorOnApiTransportException() throws ApiTransportException {
		when(pvtApi.signUp(anyString(), anyString(), anyString())).thenThrow(new ApiTransportException(""));
		SignUpResult result = signUp.execute("Test User", "user@example.com", "hunter2");
		assertTrue(result.hasError(SignUpResult.UNKNOWN_ERROR));
	}
	
	@Test
	public void testResultIsOkOnSuccess() throws ApiTransportException {
		when(pvtApi.signUp(anyString(), anyString(), anyString())).thenReturn(new ApiResponse(200, ""));
		SignUpResult result = signUp.execute("Test User", "user@example.com", "hunter2");
		assertTrue(result.isOk());
	}
	
	@Test
	public void testResultIncludesUserOnSuccess() throws ApiTransportException {
		when(pvtApi.signUp(anyString(), anyString(), anyString())).thenReturn(new ApiResponse(200, "{ \"response\": { \"user\": { \"id\": 10001, \"name\": \"Test User\", \"email\": \"user@example.com\" }, \"access_token\": \"token\" } }"));
		SignUpResult result = signUp.execute("Test User", "user@example.com", "hunter2");
		User user = result.user();
		assertEquals(10001, user.id());
		assertEquals("Test User", user.name());
		assertEquals("user@example.com", user.email());
	}
	
}
