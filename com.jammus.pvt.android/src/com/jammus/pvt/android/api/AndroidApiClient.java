package com.jammus.pvt.android.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.jammus.pvt.api.ApiClient;
import com.jammus.pvt.api.ApiResponse;
import com.jammus.pvt.api.ApiTransportException;
import com.jammus.pvt.core.PvtResult;

public class AndroidApiClient implements ApiClient {
	private static final String BASE_URL = "http://pvt-api.eu01.aws.af.cm";
	
	public String submitResult(String accessToken, PvtResult result) throws ApiTransportException {
		List<NameValuePair> postParams = prepareParameters(accessToken, result);
		return post("/report", postParams).message();
	}
	
	private ApiResponse post(String url, List<NameValuePair> params) throws ApiTransportException {
		HttpPost httpPost = new HttpPost(BASE_URL + url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			response = httpClient.execute(httpPost);
		} catch (UnsupportedEncodingException e) {
			throw new ApiTransportException(e.getMessage());
		} catch (ClientProtocolException e) {
			throw new ApiTransportException(e.getMessage());
		} catch (IOException e) {
			throw new ApiTransportException(e.getMessage());
		}
		
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
	
	private List<NameValuePair> prepareParameters(String accessToken, PvtResult result) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>(4);
		postParams.add(new BasicNameValuePair("access_token", accessToken));
		postParams.add(new BasicNameValuePair("timestamp", String.valueOf(result.date().getTime() / 1000)));
		postParams.add(new BasicNameValuePair("errors", String.valueOf(result.errorCount())));
		postParams.add(new BasicNameValuePair("response_times", responseTimesToString(result.responseTimes())));
		return postParams;
	}
	
	private String responseTimesToString(float[] times) {
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < times.length; index++) {
			if (index > 0) {
				sb.append(",");
			}
			sb.append(String.valueOf(times[index]));
		}
		return sb.toString();
	}
}
