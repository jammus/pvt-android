package com.jammus.pvt.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PvtResult {
	
	private List<Float> responseTimes;
	private int errors;
	private Date date;
	
	public PvtResult() {
		this.date = new Date();
		this.errors = 0;
		this.responseTimes = new ArrayList<Float>();
	}
	
	public PvtResult(Date date, float[] responseTimes, int errors) {
		this.date = date;
		this.responseTimes = new ArrayList<Float>();
		for (float responseTime : responseTimes) {
			this.responseTimes.add(Float.valueOf(responseTime));
		}
		this.errors = errors;
	}
	
	public Date date() {
		return date;
	}
	
	public void addError() {
		errors++;
	}
	
	public void addResponseTime(float responseTime) {
		responseTimes.add(responseTime);
	}
	
	public float averageRt() {
		if (responseTimes.size() <= 0) {
			return 0;
		}
		float total = 0;
		for (int i = 0; i < responseTimes.size(); i++) {
			total += responseTimes.get(i).floatValue();
		}
		return total / responseTimes.size();
	}
	
	public int errorCount() {
		return errors;
	}
	
	public float[] responseTimes() {
		float[] times = new float[responseTimes.size()];
		for (int i = 0; i < responseTimes.size(); i++) {
			times[i] = responseTimes.get(i).floatValue();
		}
		return times;
	}
	
}
