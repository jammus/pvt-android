package com.jammus.pvt;

import java.util.Date;

public class PvtResults {
	private float[] scores;
	private int errors;
	private Date date;
	private int scoreIndex;
	
	public PvtResults() {
		this(10);
	}
	
	public PvtResults(int rounds) {
		this.date = new Date();
		this.errors = 0;
		this.scores = new float[rounds];
		this.scoreIndex = 0;
	}
	
	public PvtResults(Date date, float[] scores, int errors) {
		this.date = date;
		this.scores = scores;
		this.scoreIndex = scores.length;
		this.errors = errors;
	}
	
	public Date date() {
		return date;
	}
	
	public void addError() {
		errors++;
	}
	
	public void addScore(float score) {
		scores[scoreIndex++] = score;
	}
	
	public float averageRt() {
		if (scoreIndex <= 0) {
			throw new IllegalStateException("Cannot calculate averate response time as no test results have been recorded.");
		}
		float total = 0;
		for (int i = 0; i < scoreIndex; i++) {
			total += scores[i];
		}
		return  total / scoreIndex;
	}
	
	public int errorCount() {
		return errors;
	}
	
	public float[] scores() {
		return scores;
	}
}
