package com.jammus.pvt.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.jammus.pvt.PvtResult;

public class PvtResultsAverageResponseTimeTest {
	private PvtResult result;
	
	@Before
	public void setUp() {
		result = new PvtResult();
	}

	@Test
	public void emptyResultThrowsException() {
		try {
			result.averageRt();
		}
		catch (IllegalStateException e) {
			return;
		}
		fail("Requesting averageRt should throw when no tests taken.");
	}
	
	@Test
	public void singleTimeIsAlsoAverateRt() {
		result.addResponseTime(100.64f);
		assertEquals(result.averageRt(), 100.64f, 0);
	}
	
	@Test
	public void multipleIdenticalTimesIsAlsoAverageRt() {
		result.addResponseTime(410.81f);
		result.addResponseTime(410.81f);
		result.addResponseTime(410.81f);
		result.addResponseTime(410.81f);
		assertEquals(result.averageRt(), 410.81f, 0);
	}
	
	@Test
	public void averageRtIsAverageOfAllTimes() {
		result.addResponseTime(234.45f);
		result.addResponseTime(873.3f);
		result.addResponseTime(78.0f);
		result.addResponseTime(927.8f);
		result.addResponseTime(413.9f);
		assertEquals(result.averageRt(), 505.49f, 0);
	}
	
	@Test
	public void averageRtIsAverageOfPrepopulatedScores() {
		float[] times = new float[] { 
			234.45f,
			873.3f,
			78.0f,
			927.8f,
			413.9f
		};
		result = new PvtResult(new Date(), times, 0);
		assertEquals(result.averageRt(), 505.49f, 0);
	}
}
