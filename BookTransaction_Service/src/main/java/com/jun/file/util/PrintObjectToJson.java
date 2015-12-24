package com.jun.file.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class PrintObjectToJson {
	public static void print(HttpServletResponse resp, int status,
			String remind, Object object) {
		PrintWriter printWriter = null;
		try {
			printWriter = resp.getWriter();
			BaseEntity baseEntity = new BaseEntity(status, remind, object);
			Gson gson = new Gson();
			printWriter.print(gson.toJson(baseEntity));
			System.out.println("baseEntity to String :"+gson.toJson(baseEntity));
			System.out.println("entity to String :"+gson.toJson(baseEntity.getValue()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
