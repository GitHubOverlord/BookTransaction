package com.jun.file.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jun.file.constant.Constant;

public class DoNoLogin {
	public static void print(HttpServletResponse resp) {
		PrintWriter printWriter;
		try {
			printWriter = resp.getWriter();
			BaseEntity baseEntity = new BaseEntity(Constant.STATUS_NO_LOGIN, "您还没有登录", "fail");
			Gson gson2 = new Gson();
			String jsonString = gson2.toJson(baseEntity);
			printWriter.print(jsonString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
