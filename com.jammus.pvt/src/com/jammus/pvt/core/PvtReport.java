package com.jammus.pvt.core;

import java.util.Date;

public class PvtReport {

	private Date date;
	private int errors;
	private int lapses;
	private float averageRt;
	
	public PvtReport(Date date, int errors, int lapses, float averageRt)
	{
		this.date = date;
		this.errors = errors;
		this.lapses = lapses;
		this.averageRt = averageRt;
	}

	public Date date() {
		return date;
	}

	public int errors() {
		return errors;
	}

	public int lapses() {
		return lapses;
	}

	public float averageRt() {
		return averageRt;
	}

}
