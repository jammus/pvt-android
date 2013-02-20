package com.jammus.pvt.interactors;

import java.util.Arrays;
import java.util.List;

import com.jammus.pvt.core.PvtReport;

public class SubmitPvtResult extends Result {

	public static final int NOT_AUTHORISED = -40001;
	public static final int UNABLE_TO_LOAD_REPORT = -40002;
	
	private PvtReport report;
	
	public SubmitPvtResult(int error) {
		super(Arrays.asList(error));
	}

	public SubmitPvtResult() {
		super();
	}

	public SubmitPvtResult(List<Integer> errors, PvtReport report) {
		super(errors);
		this.report = report;
	}

	public PvtReport report() {
		return report;
	}

}
