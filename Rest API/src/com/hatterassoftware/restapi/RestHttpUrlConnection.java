package com.hatterassoftware.restapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import android.util.Base64;
import android.util.Log;

public class RestHttpUrlConnection {
	
	private HttpURLConnection connection;
	
	final String TAG = "RestHttpUrlConnection";

	protected RestHttpUrlConnection(URL url, HashMap<String,String> headers, final String username, final String password) throws IOException {
		connection = (HttpURLConnection) url.openConnection();
		
		if(headers != null) {
			for(String key: headers.keySet()) {
				Log.d(TAG, "Adding header: " + key + "; " + headers.get(key));
				connection.addRequestProperty(key, headers.get(key));
			}
		}
		
		if(username != null) {
			String auth = "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.DEFAULT);
			
			connection.addRequestProperty("Authorization", auth);
		}
	}
	
	public HttpReturn doPostRequest(String postData) throws IOException {
		return doPostOrPut(true, postData);
	}
	
	public HttpReturn doGetRequest() throws IOException {
		return doGetOrDelete(true);
	}
	
	public HttpReturn doPutRequest(String putData) throws IOException {
		return doPostOrPut(false, putData);
	}
	
	public HttpReturn doDeleteRequest() throws IOException {
		return doGetOrDelete(false);
	}
	
	private HttpReturn doGetOrDelete(boolean isGet) throws ProtocolException, IOException {
		if (isGet) {
			connection.setRequestMethod("GET");
		} else {
			connection.setRequestMethod("DELETE");
		}
		
		String line;
		StringBuffer output = new StringBuffer(1024);
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = in.readLine()) != null) output.append(line);
		} catch (Exception e) {
			HttpReturn httpReturn = new HttpReturn(connection.getResponseCode(), output.toString());
			httpReturn.restException = new RestException(e);
			return httpReturn;
		}
		
		return new HttpReturn(connection.getResponseCode(), output.toString());
	}
	
	private HttpReturn doPostOrPut(boolean isPost, String data) throws ProtocolException, IOException {
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setFixedLengthStreamingMode(data.getBytes().length);
		if (isPost) {
			connection.setRequestMethod("POST");
		} else {
			connection.setRequestMethod("PUT");
		}
		
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.writeBytes(data);
		out.flush();
		out.close();
		
		connection.connect();
		
		String line;
		StringBuffer output = new StringBuffer(1024);
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = in.readLine()) != null) output.append(line);
		} catch (Exception e) {
			HttpReturn httpReturn = new HttpReturn(connection.getResponseCode(), output.toString());
			httpReturn.restException = new RestException(e);
			return httpReturn;
		}
		
		return new HttpReturn(connection.getResponseCode(), output.toString());		
	}
}
