package com.jammus.pvt.api;

import com.jammus.pvt.core.PvtResult;
import com.jammus.pvt.core.User;

public interface ApiClient {
	String submitResult(String accessToken, PvtResult result) throws ApiTransportException;
}
