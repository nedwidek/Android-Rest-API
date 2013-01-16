package com.hatterassoftware.restapi;

import java.net.URL;
import java.util.HashMap;

import android.os.AsyncTask;

/**
 * An AsyncTask implementation for performing POSTs on the Hypothetical REST APIs.
 */
public class PutTask extends AsyncTask<String, String, HttpReturn>{
    private String restUrl;
    private RestCallback callback;
    private HashMap<String, String> headers;
    private String requestBody;
    private String username;
    private String password;

    /**
     * Creates a new instance of PostTask with the specified URL, callback, and
     * request body.
     * 
     * @param restUrl The URL for the REST API.
     * @param callback The callback to be invoked when the HTTP request
     *            completes.
     * @param requestBody The body of the POST request.
     * 
     */
    public PutTask(String restUrl, String requestBody, RestCallback callback, HashMap<String, String> headers, String username, String password){
    	this.restUrl = restUrl;
        this.requestBody = requestBody;
        this.callback = callback;
        this.headers = headers;
        this.username = username;
        this.password = password;
    }

    @Override
    protected HttpReturn doInBackground(String... params) {
    	try {
    		RestHttpUrlConnection request = new RestHttpUrlConnection(new URL(restUrl), headers, username, password);
    		return request.doPutRequest(requestBody);
    	} catch (Throwable t) {
    		return new HttpReturn(new RestException(t));
    	}
    }

    @Override
    protected void onPostExecute(HttpReturn result) {
        callback.onTaskComplete(result);
        super.onPostExecute(result);
    }
}