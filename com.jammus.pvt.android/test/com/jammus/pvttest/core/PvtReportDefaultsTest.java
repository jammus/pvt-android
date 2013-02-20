package com.jammus.pvttest.core;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.jammus.pvt.core.PvtReport;

public class PvtReportDefaultsTest {

	@Test
	public void testConstructorConfiguresProperties() {
		Date date = new Date();
		date.setTime(1234567890);
		PvtReport pvtReport = new PvtReport(
			date,
			12,
			14,
			654.54f
		);
		
		assertEquals(1234567890, pvtReport.date().getTime());
		assertEquals(12, pvtReport.errors());
		assertEquals(14, pvtReport.lapses());
		assertEquals(654.54f, pvtReport.averageRt(), 0.0f);
	}

}
