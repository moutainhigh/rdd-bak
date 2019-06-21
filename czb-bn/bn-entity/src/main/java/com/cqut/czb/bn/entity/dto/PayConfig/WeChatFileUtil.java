package com.cqut.czb.bn.entity.dto.PayConfig;

import javax.servlet.ServletInputStream;
import java.io.*;

public class WeChatFileUtil {

	private static String escapedChars = "&quot;";
	private static String customaryChar = "\"";

	/**
	 * 流转字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String inputStream2String(ServletInputStream is) {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";

		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static String saveImgFile(InputStream inputStream, String fileName) {
		String pathToDataBase = "/TinklingCat/images/" + fileName;
		String imgFilePath = "/" + getRootPath() + "/images/" + fileName;
		try {
			OutputStream os = new FileOutputStream(imgFilePath);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pathToDataBase;
	}

	/**
	 * 获取项目根目录
	 */
	public static String getRootPath() {
		WeChatFileUtil r = new WeChatFileUtil();
		String path = r.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.indexOf("/WEB-INF"));
		return path;
	}

	/**
	 * 根据文件获取 输入流
	 */
	public static FileInputStream getInputStreame(String file) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(new File(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileInputStream;
	}

}
