package com.jammus.pvt.android.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.jammus.pvt.api.ApiClient;
import com.jammus.pvt.api.ApiResponse;
import com.jammus.pvt.api.ApiTransportException;

public class AndroidApiClient implements ApiClient {
	
	protected static final String BASE_URL = "http://pvt-api.eu01.aws.af.cm";
	
	public ApiResponse post(String url, Dictionary<String, String> params) throws ApiTransportException {
		return post(url, params, new Hashtable<String, String>());
	}
	
	public ApiResponse post(String url, Dictionary<String, String> params,
			Dictionary<String, String> headers) throws ApiTransportException {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>(4);
		Enumeration<String> enumeration = params.keys();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			postParams.add(new BasicNameValuePair(key, params.get(key)));
		}
		HttpPost httpPost = new HttpPost(BASE_URL + url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(postParams));
		} catch (UnsupportedEncodingException e) {
			throw new ApiTransportException(e.getMessage());
		}
		
		enumeration = headers.keys();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			httpPost.addHeader(key, headers.get(key));
		}
		
		try {
			response = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			throw new ApiTransportException(e.getMessage());
		} catch (IOException e) {
			throw new ApiTransportException(e.getMessage());
		}
	
		return decodeResponse(response);
	}

	public ApiResponse get(String url, Dictionary<String, String> headers) throws ApiTransportException {
		HttpGet httpGet = new HttpGet(BASE_URL + url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		
		Enumeration<String> enumeration = headers.keys();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			httpGet.addHeader(key, headers.get(key));
		}
		
		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			throw new ApiTransportException(e.getMessage());
		} catch (IOException e) {
			throw new ApiTransportException(e.getMessage());
		}
		
		return decodeResponse(response);
	}
	
	private ApiResponse decodeResponse(HttpResponse response) throws ApiTransportException
	{
		HttpEntity httpEntity = response.getEntity();
		if (httpEntity == null) {
			return null;
		}
		
		StatusLine statusLine = response.getStatusLine();
		InputStream inputStream;
		try {
			inputStream = httpEntity.getContent();
		} catch (IllegalStateException e) {
			throw new ApiTransportException(e.getMessage());
		} catch (IOException e) {
			throw new ApiTransportException(e.getMessage());
		}
		
		try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String s;
			while (true) {
				s = buffer.readLine();
				if (s == null || s.length() == 0) {
					break;
				}
				sb.append(s);
			}
			buffer.close();
			inputStream.close();
			return new ApiResponse(statusLine.getStatusCode(), sb.toString());
		} catch (UnsupportedEncodingException e) {
			throw new ApiTransportException(e.getMessage());
		} catch (IOException e) {
			throw new ApiTransportException(e.getMessage());
		}
	
	}
	
}
