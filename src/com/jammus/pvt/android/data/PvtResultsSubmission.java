package com.jammus.pvt.android.data;

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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.jammus.pvt.core.PvtResult;

public class PvtResultsSubmission {
	private static final String POST_URL = "http://ec2-176-34-215-74.eu-west-1.compute.amazonaws.com:8080/report/";
	
	public String submit(int userId, PvtResult result) {
		
		List<NameValuePair> postParams = prepareParameters(userId, result);
		
		HttpPost httpPost = new HttpPost(POST_URL);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(postParams));
			response = httpClient.execute(httpPost);
		} catch (UnsupportedEncodingException e) {
			return e.getMessage();
		} catch (ClientProtocolException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}
		
		HttpEntity httpEntity = response.getEntity();
		if (httpEntity == null) {
			return null;
		}
		
		InputStream inputStream;
		try {
			inputStream = httpEntity.getContent();
		} catch (IllegalStateException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
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
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	private List<NameValuePair> prepareParameters(int userId, PvtResult result) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>(4);
		postParams.add(new BasicNameValuePair("user_id", String.valueOf(userId)));
		postParams.add(new BasicNameValuePair("date", String.valueOf(result.date())));
		postParams.add(new BasicNameValuePair("errorCount", String.valueOf(result.errorCount())));
		postParams.add(new BasicNameValuePair("averageRt", String.valueOf(result.averageRt())));
		postParams.add(new BasicNameValuePair("responseTimes", responseTimesToString(result.responseTimes())));
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
