package com.jammus.pvttest.core;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jammus.pvt.core.User;

public class UserSurveyTest {

	@Test
	public void testHasNotCompletedScreeningSurveyByDefault() {
		User user = new User(0, null, null, null);
		assertFalse(user.hasCompletedScreening());
	}
	
	@Test
	public void testCanConfigureCompletionInConstructor() {
		User user = new User(0, null, null, null, true);
		assertTrue(user.hasCompletedScreening());
	}

}
