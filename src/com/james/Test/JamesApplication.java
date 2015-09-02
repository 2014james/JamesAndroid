package com.james.Test;

import com.iflytek.cloud.SpeechUtility;

import android.app.Application;

/***
 * @author James
 * @Package com.james.Test
 * @Title: JamesApplication.java
 * @Description
 * @date 2015-9-2
 * @version 1.0
 * 
 */
public class JamesApplication extends Application {

	@Override
	public void onCreate() {
		//科大
		SpeechUtility.createUtility(getApplicationContext(), "appid=55e69afe");
		super.onCreate();
	}

}
