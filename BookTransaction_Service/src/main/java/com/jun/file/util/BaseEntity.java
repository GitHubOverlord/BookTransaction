package com.jun.file.util;

public class BaseEntity {
	private int status;//请求返回的状态码
	private String sessionId;//这个是sessionID,因为我们不做持久化登录，所以不需要这个了
	private String response;//响应的提示文字
	private Object value;//返回真正内容

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
