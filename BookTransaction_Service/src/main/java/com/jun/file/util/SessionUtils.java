package com.jun.file.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jun.booktransaction.bean.UserBean;
import com.opensymphony.xwork2.ActionContext;

public class SessionUtils {
	public static final String USER_SESSION = "user";

	public static UserBean getSession() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return (UserBean) session.get(USER_SESSION);
	}

	public static synchronized void removeSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		actionContext.getSession().remove(name);

	}

	public static synchronized void putSession(Object object) {
		ActionContext.getContext().getSession().put(USER_SESSION, object);
	}

	public static synchronized void clearSession(HttpServletRequest request) {
		ActionContext.getContext().getSession().clear();
	}
}
