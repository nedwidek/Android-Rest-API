package com.hatterassoftware.restapi;

public class RestException extends Exception {
	
	private static final long serialVersionUID = 6349608845708680800L;

	public RestException(Throwable child) {
		super(child);
	}
}
