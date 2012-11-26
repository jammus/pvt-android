package com.jammus.pvt.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.jammus.pvt.PvtResults;

public class PvtResultsAverageResponseTimeTest {
	private PvtResults results;;
	
	@Before
	public void setUp() {
		results = new PvtResults();
	}

	@Test
	public void emptyResultsThrowsException() {
		try {
			results.averageRt();
		}
		catch (IllegalStateException e) {
			return;
		}
		fail("Requesting averageRt should throw when no tests taken.");
	}
	
	@Test
	public void singleResultIsAlsoAverateRt() {
		results.addScore(100.64f);
		assertEquals(results.averageRt(), 100.64f, 0);
	}
	
	@Test
	public void multipleIdenticalResultsIsAlsoAverageRt() {
		results.addScore(410.81f);
		results.addScore(410.81f);
		results.addScore(410.81f);
		results.addScore(410.81f);
		assertEquals(results.averageRt(), 410.81f, 0);
	}
	
	@Test
	public void averageRtIsAverageOfAllResults() {
		results.addScore(234.45f);
		results.addScore(873.3f);
		results.addScore(78.0f);
		results.addScore(927.8f);
		results.addScore(413.9f);
		assertEquals(results.averageRt(), 505.49f, 0);
	}
	
	@Test
	public void averageRtIsAverageOfPrepopulatedScores() {
		float[] scores = new float[] { 
			234.45f,
			873.3f,
			78.0f,
			927.8f,
			413.9f
		};
		results = new PvtResults(new Date(), scores, 0);
		assertEquals(results.averageRt(), 505.49f, 0);
	}
}
