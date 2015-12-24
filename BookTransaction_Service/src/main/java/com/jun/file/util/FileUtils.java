package com.jun.file.util;

import java.io.File;

import org.apache.struts2.ServletActionContext;

public class FileUtils {
	public FileUtils() {

	}

	public static String createFile(String url) {
		String absPath = ServletActionContext.getServletContext().getRealPath(
				url);
		File file = new File(absPath);
		file.mkdirs();
		return absPath;
	}

	public static boolean saveFile(File tempFile, String filePath,
			String fileFileName) {
		try {
			File savefile = new File(new File(filePath), fileFileName);
			if (!savefile.getParentFile().exists())
				savefile.getParentFile().mkdirs();
			org.apache.commons.io.FileUtils.copyFile(tempFile, savefile);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static void downloadFile() {

	}

	public static boolean deleteFile(String path) {
		boolean flag = true;
		try {
			File file = new File(path);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
}
