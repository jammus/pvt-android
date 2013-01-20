package com.jammus.pvt.api;

import java.util.Dictionary;

public interface ApiClient {
	ApiResponse post(String url, Dictionary<String, String> parameters) throws ApiTransportException;
}
