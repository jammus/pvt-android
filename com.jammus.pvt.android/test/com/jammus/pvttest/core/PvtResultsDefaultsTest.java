package com.jammus.pvttest.core;

import java.util.Date;

import com.jammus.pvt.core.PvtResult;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PvtResultsDefaultsTest {

	private PvtResult result;
	
	@Before
	public void setUp() {
		result = new PvtResult();
	}
	
	@Test
	public void dateIsToday() {
		long expectedDate = new Date().getTime();
		long actualDate = result.date().getTime();
		
		long difference = Math.abs(expectedDate - actualDate);
		
		assertTrue(difference <= 1000);
	}
	
	@Test
	public void errorCountIsZero() {
		assertEquals(result.errorCount(), 0);
	}
	
	@Test
	public void numberOfRoundsIs10() {
		assertEquals(result.responseTimes().length, 10);
	}

	@Test
	public void scoresIsEmptyArray() {
		float[] responseTimes = result.responseTimes();
		for (int i = 0; i < 10; i++) {
			assertEquals(0, responseTimes[i], 0);
		}
	}
}
