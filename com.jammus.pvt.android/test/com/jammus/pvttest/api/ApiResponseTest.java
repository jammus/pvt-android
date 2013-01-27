package com.jammus.pvttest.api;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.jammus.pvt.api.ApiResponse;

public class ApiResponseTest {

	@Test
	public void testConstructorConfiguresCodeAndMessage() {
		ApiResponse response = new ApiResponse(200, "Hello");
		assertEquals(200, response.code());
		assertEquals("Hello", response.message());
	}

	@Test
	public void testJsonMessagesCanBeDecoded() throws JSONException {
		ApiResponse response = new ApiResponse(200, "{ \"message\": \"Hello\" } ");
		JSONObject json = response.json();
		assertEquals("Hello", json.getString("message"));
	}
	
	@Test
	public void testInvalidJsonReturnsEmptyObject() {
		ApiResponse response = new ApiResponse(200, "");
		JSONObject json = response.json();
		assertNotNull(json);
	}
}
