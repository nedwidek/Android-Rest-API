package com.hatterassoftware.restapi;

/**
 * Implement on classes that receive data back from Rest API
 * @author nedwidek
 *
 */
public interface RestCallback {
	/**
     * Called when the response data for the REST call is ready. <br/>
     * This method is guaranteed to execute on the UI thread.
     * 
     * @param httpReturn Contains the HTTP response code and the body of the response.
     */
    abstract void onTaskComplete(HttpReturn httpReturn);

    /**
     * Called when a POST success response is received. <br/>
     * This method is guaranteed to execute on the UI thread.
     */
    public abstract void onPostSuccess();

}
