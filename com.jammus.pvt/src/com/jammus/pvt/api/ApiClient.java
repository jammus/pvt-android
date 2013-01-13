package com.jammus.pvt.api;

import com.jammus.pvt.core.PvtResult;
import com.jammus.pvt.core.User;

public interface ApiClient {
	String submitResult(User user, PvtResult result) throws ApiTransportException;
}
