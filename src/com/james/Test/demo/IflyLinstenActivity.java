package com.james.Test.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechError;
import com.james.Test.R;
import com.james.Test.ifly.IflySpeechRecognizer;
import com.james.Test.utils.JsonParserUtil;
import com.james.Test.utils.MobileUtil;

/***
 * @author James
 * @Package com.james.Test.demo
 * @Title: IflyLinstenActivity.java
 * @Description 语音识别测试
 * @date 2015-9-2
 * @version 1.0
 * 
 */
public class IflyLinstenActivity extends Activity implements OnClickListener {

	Button mButton;
	TextView mTextView;
	boolean isStart = false;
	Context mContext = this;
	String TAG = "IflyLinstenActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ifly);
		mButton = (Button) findViewById(R.id.ifly_btn);
		mTextView = (TextView) findViewById(R.id.ifly_tv);
		mTextView.setText("识别结果：");
		mButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		IflySpeechRecognizer.beginIflySR(mContext, null, mListener);
		mTextView.setText("识别结果：");
		mButton.setText("识别中...");
	}

	RecognizerListener mListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {

			MobileUtil.setVolumeMute(mContext);
			Log.i(TAG, "onBeginOfSpeech");
		}

		@Override
		public void onEndOfSpeech() {
			Log.i(TAG, "onEndOfSpeech");
		}

		@Override
		public void onError(SpeechError arg0) {
			Log.i(TAG, "onError");
			Log.i(TAG, "onError-->" + arg0.getErrorCode());
			mButton.setText("识别异常：" + arg0.getErrorCode()
					+ arg0.getErrorDescription());
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			Log.i(TAG, "onEvent");
		}

		@Override
		public void onResult(com.iflytek.cloud.RecognizerResult arg0,
				boolean arg1) {
			Log.i(TAG, "onResult");
			String text = JsonParserUtil.parseIatResult(arg0.getResultString());

			mTextView.setText("识别结果：\n" + text);
			mButton.setText("识别结束");

			IflySpeechRecognizer.stopIflySpeechRecognizer();
		}

		@Override
		public void onVolumeChanged(int arg0) {

		}

	};
}
