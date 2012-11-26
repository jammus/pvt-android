package com.jammus.pvt;

import java.util.Date;

public class PvtResults {
	private float[] scores;
	private int errors;
	private Date date;
	private int scoreIndex;
	
	public PvtResults(Date date) {
		this(date, 10);
	}
	
	public PvtResults(Date date, int rounds) {
		this.date = date;
		this.errors = 0;
		this.scores = new float[rounds];
		this.scoreIndex = 0;
	}
	
	public PvtResults(Date date, int errors, float[] scores) {
		this.date = date;
		this.errors = errors;
		this.scores = scores;
		this.scoreIndex = scores.length + 1;
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
