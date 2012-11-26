package com.jammus.pvt.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.jammus.pvt.PvtResult;

public class PvtResultsErrorCountTest {

	@Test
	public void canAddErrors() {
		PvtResult results = new PvtResult();
		results.addError();
		results.addError();
		results.addError();
		assertEquals(results.errorCount(), 3);
	}

	@Test
	public void errorCountCanBeConfiguredAtConstruction() {
		PvtResult results = new PvtResult(new Date(), new float[] { }, 45);
		assertEquals(results.errorCount(), 45);
	}
}
