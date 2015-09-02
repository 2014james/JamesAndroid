package com.james.Test.ifly;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.james.Test.utils.FileUtil;

/**
 * 讯飞语音监听 只返回你说的话
 * 
 * @author James 
 * 
 */
public class IflySpeechRecognizer {
	// 语音识别对象
	private static SpeechRecognizer mSpeechRecognizer = null;
	// 云端语法文件
	private static String mCloudGrammar = null;
	// 本地语法构建路径
	private static String grmPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/msc/test";

	private final static String GRAMMAR_TYPE_ABNF = "abnf";
	private static String mContent = null;

	public IflySpeechRecognizer() {
	}

	/**
	 * 构建语音识别的语法
	 * */
	public static void initCloudGrammar(Context context,
			GrammarListener grammarListener) {

		mSpeechRecognizer = SpeechRecognizer.getRecognizer();
		if (mSpeechRecognizer == null) {
			mSpeechRecognizer = SpeechRecognizer
					.createRecognizer(context, null);
		}
		mCloudGrammar = FileUtil.readFile(context, "grammar_sample.abnf",
				"utf-8");
		mContent = new String(mCloudGrammar);
		mSpeechRecognizer.buildGrammar(GRAMMAR_TYPE_ABNF, mContent,
				grammarListener);

	}

	/**
	 * 开始语音监听
	 * 
	 * @param context
	 * @param grammarId
	 * @param sRListener
	 *            监听语音处理者
	 */
	public static void beginIflySR(Context context, String grammarId,
			RecognizerListener sRListener) {
		mSpeechRecognizer = SpeechRecognizer.getRecognizer();
		if (mSpeechRecognizer == null) {
			mSpeechRecognizer = SpeechRecognizer
					.createRecognizer(context, null);
		}
		// 设置返回结果格式
		mSpeechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
		if (TextUtils.isEmpty(grammarId)) {
			mSpeechRecognizer.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
		} else {

			// 设置云端识别使用的语法id
			mSpeechRecognizer.setParameter(SpeechConstant.CLOUD_GRAMMAR,
					grammarId);

		}
		// // 使用引擎，使用本地语记app识别
		// mSpeechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE,
		// SpeechConstant.TYPE_LOCAL);
		mSpeechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
		// 设置语言
		// 设置语言区域

		mSpeechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
		// 设置语音前端点
		mSpeechRecognizer.setParameter(SpeechConstant.VAD_BOS, "5000");
		// 设置语音后端点
		// mSpeechRecognizer.setParameter(SpeechConstant.VAD_EOS, "2000");
		mSpeechRecognizer.setParameter(SpeechConstant.VAD_EOS, "1650");
		// 设置标点符号( 1 加上符号,0 不加)
		mSpeechRecognizer.setParameter(SpeechConstant.ASR_PTT, "1");

		// 设置是否会话过程中暂停后台音乐播放
		mSpeechRecognizer.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "1");

		mSpeechRecognizer.setParameter(SpeechConstant.IVW_THRESHOLD, "0");

		mSpeechRecognizer.startListening(sRListener);

	}

	/**
	 * 返回是否正在监听
	 * 
	 * @return
	 */
	public static boolean isListening() {
		if (mSpeechRecognizer != null) {
			return mSpeechRecognizer.isListening();
		} else {
			return false;
		}

	}

	/***
	 * 停止监听
	 */
	public static void stopListening() {
		if (mSpeechRecognizer != null)
			mSpeechRecognizer.stopListening();

	}

	/**
	 * 取消监听
	 */
	public static void cancel() {
		if (mSpeechRecognizer != null) {
			mSpeechRecognizer.cancel();
			mSpeechRecognizer.destroy();
		}

	}

	/**
	 * 销毁监听
	 */
	public static void stopIflySpeechRecognizer() {

		if (IflySpeechRecognizer.isListening()) {
			IflySpeechRecognizer.stopListening();
			IflySpeechRecognizer.cancel();
		} else {
			IflySpeechRecognizer.cancel();
		}
	}

}
