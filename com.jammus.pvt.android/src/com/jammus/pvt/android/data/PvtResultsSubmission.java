package com.jammus.pvt.android.data;

import com.jammus.pvt.android.api.AndroidApiClient;
import com.jammus.pvt.core.PvtResult;
import com.jammus.pvt.core.User;

public class PvtResultsSubmission {
	private AndroidApiClient client;
	
	public PvtResultsSubmission(AndroidApiClient client)
	{
		this.client = client;
	}
	
	public String submit(User user, PvtResult result) {}

}
