package com.jun.file.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseActionSupport extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {

	/**
	 * actionSupport的基类
	 */
	/**
	 * request对象
	 */
	protected HttpServletRequest request;
	/**
	 * response对象
	 */
	protected HttpServletResponse response;
	private static final long serialVersionUID = 3037946836778567823L;

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}
}
