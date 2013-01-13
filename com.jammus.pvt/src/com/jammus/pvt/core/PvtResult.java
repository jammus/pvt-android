package com.jammus.pvt.core;

import java.util.Date;

public class PvtResult {
	private float[] responseTimes;
	private int errors;
	private Date date;
	private int timeIndex;
	
	public PvtResult() {
		this(10);
	}
	
	public PvtResult(int rounds) {
		this.date = new Date();
		this.errors = 0;
		this.responseTimes = new float[rounds];
		this.timeIndex = 0;
	}
	
	public PvtResult(Date date, float[] responseTimes, int errors) {
		this.date = date;
		this.responseTimes = responseTimes;
		this.timeIndex = responseTimes.length;
		this.errors = errors;
	}
	
	public Date date() {
		return date;
	}
	
	public void addError() {
		errors++;
	}
	
	public void addResponseTime(float responseTime) {
		responseTimes[timeIndex++] = responseTime;
	}
	
	public float averageRt() {
		if (timeIndex <= 0) {
			throw new IllegalStateException("Cannot calculate average response time as no test results have been recorded.");
		}
		float total = 0;
		for (int i = 0; i < timeIndex; i++) {
			total += responseTimes[i];
		}
		return  total / timeIndex;
	}
	
	public int errorCount() {
		return errors;
	}
	
	public float[] responseTimes() {
		return responseTimes;
	}
}
