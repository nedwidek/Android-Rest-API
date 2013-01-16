package com.hatterassoftware.restapi;

import java.net.URL;
import java.util.HashMap;

import android.os.AsyncTask;
import android.util.Log;

/**
 * An AsyncTask implementation for performing POSTs on the Hypothetical REST APIs.
 */
public class GetTask extends AsyncTask<String, String, HttpReturn>{
    private String restUrl;
    private RestCallback callback;
    private HashMap<String, String> headers;
    private String username;
    private String password;
    
    final String TAG = "GetTask";

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
    public GetTask(String restUrl, RestCallback callback, HashMap<String, String> headers, String username, String password){
    	this.restUrl = restUrl;
        this.callback = callback;
        this.headers = headers;
        this.username = username;
        this.password = password;
    }

    @Override
    protected HttpReturn doInBackground(String... params) {
    	try {
    		RestHttpUrlConnection request = new RestHttpUrlConnection(new URL(restUrl), headers, username, password);
    		return request.doGetRequest();
    	} catch (Throwable t) {
    		Log.e(TAG, "Exception in doInBackground", t);
    		return new HttpReturn(new RestException(t));
    	}
    }

    @Override
    protected void onPostExecute(HttpReturn result) {
    	Log.d(TAG, "Entered onPostExecute");
    	Log.d(TAG, "result.status = " + result.status);
    	Log.d(TAG, "result.content = " + result.content);
        callback.onTaskComplete(result);
        super.onPostExecute(result);
    }
}