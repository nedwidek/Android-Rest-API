package com.hatterassoftware.restapi;

public class HttpReturn {

	public int status = -1;
	public String content = null;
	public RestException restException = null;
	
	public HttpReturn(int status, String content) {
		this.status = status;
		this.content = content;
	}
	
	public HttpReturn(RestException restException) {
		this.restException = restException;
	}
}
