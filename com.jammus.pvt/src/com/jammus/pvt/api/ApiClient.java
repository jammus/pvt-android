package com.jammus.pvt.api;

import com.jammus.pvt.core.PvtResult;

public interface ApiClient {
	String submitResult(String accessToken, PvtResult result) throws ApiTransportException;
}
