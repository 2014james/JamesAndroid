package com.james.Test.utils;

import java.io.InputStream;

import android.content.Context;

/***
 * @author James
 * @Package com.james.Test.utils
 * @Title: FileUtil.java
 * @Description
 * @date 2015-9-2
 * @version 1.0
 * 
 */
public class FileUtil {

	/**
	 * 读取assets文件夹
	 * 
	 * @return content
	 */
	public static String readFile(Context mContext, String file, String code) {
		int len = 0;
		byte[] buf = null;
		String result = "";
		try {
			InputStream in = mContext.getAssets().open(file);
			len = in.available();
			buf = new byte[len];
			in.read(buf, 0, len);

			result = new String(buf, code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
