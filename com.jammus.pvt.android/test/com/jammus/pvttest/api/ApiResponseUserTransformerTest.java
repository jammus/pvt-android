package com.jammus.pvttest.api;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jammus.pvt.api.ApiResponse;
import com.jammus.pvt.api.ApiResponseUserTransformer;
import com.jammus.pvt.core.User;

public class ApiResponseUserTransformerTest {

	@Test
	public void testConfiguresUserDetails() {
		ApiResponse response = new ApiResponse(
			200,
			" { \"user\": { \"id\": 1001, \"name\": \"Test User\", \"email\": \"user@example.com\" } } "
		);
		User user = ApiResponseUserTransformer.transform(response);
		assertEquals(1001, user.id());
		assertEquals("Test User", user.name());
		assertEquals("user@example.com", user.email());
	}

	@Test
	public void testConfiguresAccessTokenWhenPresent() {
		ApiResponse response = new ApiResponse(
			200,
			" { \"user\": { \"id\": 1001, \"name\": \"Test User\", \"email\": \"user@example.com\" }, \"access_token\": \"12345\" } "
		);
		User user = ApiResponseUserTransformer.transform(response);
		assertEquals("12345", user.token());
	}

	@Test
	public void testConfiguresScreeningSurveyCompleteToFalseWhenNotPresent() {
		ApiResponse response = new ApiResponse(
			200,
			" { \"user\": { \"id\": 1001, \"name\": \"Test User\", \"email\": \"user@example.com\" } } "
		);
		User user = ApiResponseUserTransformer.transform(response);
		assertFalse(user.hasCompletedScreening());
	}
	
	@Test
	public void testConfiguresScreeningSurveyCompleteWhenPresent() {
		ApiResponse response = new ApiResponse(
			200,
			" { \"user\": { \"id\": 1001, \"name\": \"Test User\", \"email\": \"user@example.com\", \"survey_completed\": true } } "
		);
		User user = ApiResponseUserTransformer.transform(response);
		assertTrue(user.hasCompletedScreening());
		
		response = new ApiResponse(
			200,
			" { \"user\": { \"id\": 1001, \"name\": \"Test User\", \"email\": \"user@example.com\", \"survey_completed\": false } } "
		);
		user = ApiResponseUserTransformer.transform(response);
		assertFalse(user.hasCompletedScreening());
	}

}
