package com.jammus.pvt.test;

import java.util.Date;

import com.jammus.pvt.PvtResults;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PvtResultsDefaultsTest {

	private PvtResults results;
	
	@Before
	public void setUp() {
		results = new PvtResults();
	}
	
	@Test
	public void dateIsToday() {
		long expectedDate = new Date().getTime();
		long actualDate = results.date().getTime();
		
		long difference = Math.abs(expectedDate - actualDate);
		
		assertTrue(difference <= 1000);
	}
	
	@Test
	public void errorCountIsZero() {
		assertEquals(results.errorCount(), 0);
	}
	
	@Test
	public void numberOfRoundsIs10() {
		assertEquals(results.scores().length, 10);
	}

	@Test
	public void scoresIsEmptyArray() {
		float[] scores = results.scores();
		for (int i = 0; i < 10; i++) {
			assertEquals(0, scores[i], 0);
		}
	}
}
