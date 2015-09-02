package com.james.Test.utils;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

/***
 * @author James
 * @Package com.james.Test.utils
 * @Title: MobileUtil.java
 * @Description 手机操作的工具类
 * @date 2015-9-1
 * @version 1.0
 * 
 */
public class MobileUtil {

	/**
	 * 
	 * @author James
	 * @Description 关屏
	 * @param mContext
	 */
	public static void shutOffScreen(Context mContext) {
		Intent close_screen = new Intent("com.rmt.action.SHUT_SCREEN");
		mContext.sendBroadcast(close_screen);
	}

	/**
	 * 语音监听前，先静音
	 * 
	 * @param mContext
	 */
	public static void setVolumeMute(Context mContext) {
		AudioManager mAudioManager = (AudioManager) mContext
				.getSystemService(Context.AUDIO_SERVICE);
		mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
	}
}
