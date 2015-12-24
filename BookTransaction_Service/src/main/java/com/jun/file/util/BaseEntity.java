package com.jun.file.util;

public class BaseEntity {
	private int status;
	private String sessionId;
	private String response;
	private Object value;

	public BaseEntity(int status, String response, Object object,
			String sessionId) {
		this.status = status;
		this.response = response;
		this.value = object;
		this.sessionId = sessionId;
	}

	public BaseEntity(int status, String response, Object object) {
		this.status = status;
		this.response = response;
		this.value = object;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
