package com.jammus.pvt.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.jammus.pvt.PvtResults;

public class PvtResultsErrorCountTest {

	@Test
	public void canAddErrors() {
		PvtResults results = new PvtResults();
		results.addError();
		results.addError();
		results.addError();
		assertEquals(results.errorCount(), 3);
	}

	@Test
	public void errorCountCanBeConfiguredAtConstruction() {
		PvtResults results = new PvtResults(new Date(), new float[] { }, 45);
		assertEquals(results.errorCount(), 45);
	}
}
