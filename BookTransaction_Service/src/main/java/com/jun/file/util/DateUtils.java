package com.jun.file.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jun.file.constant.Constant;


public class DateUtils {
	public static Date getDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				Constant.DATE_FROMATE);
		Date date = new Date();
		simpleDateFormat.format(date);
		return date;
	}

	public static String getNowDateString() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				Constant.DATE_FROMATE);
		return simpleDateFormat.format(date);

	}

	public static Date getDate(String dateString) {
		Date date = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				Constant.DATE_FROMATE);
		try {
			simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
