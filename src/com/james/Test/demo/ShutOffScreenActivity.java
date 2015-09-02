package com.james.Test.demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.james.Test.R;
import com.james.Test.utils.Constants;
import com.james.Test.utils.MobileUtil;
import com.james.Test.utils.SensorUtil;
import com.james.Test.utils.SensorUtil.OnShakeListener;
import com.james.Test.utils.ToastUtil;

/***
 * @author James
 * @Package com.james.Test.demo
 * @Title: ShutOffScreenTest.java
 * @Description
 * @date 2015-9-1
 * @version 1.0
 * 
 */
public class ShutOffScreenActivity extends Activity {

	private JamesBroadCast mJamesBroadCast;
	private Context mContext = ShutOffScreenActivity.this;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.activity_main_shutoffscreentest);
		mTextView = (TextView) findViewById(R.id.textView);
		mJamesBroadCast = new JamesBroadCast();
		registerBroadCast();
		super.onCreate(savedInstanceState);
	}

	public void registerBroadCast() {
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Constants.BROADCAST_IS_SLEEPCOUNT);
		registerReceiver(mJamesBroadCast, mIntentFilter);
	}

	/**
	 * 
	 * @author James
	 * @Package com.james.Test
	 * @Title: MainActivity.java
	 * @Description �㲥
	 * @date 2015-9-1
	 * @version 1.0
	 * 
	 */
	class JamesBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {

			String action = arg1.getAction();
			if (action.equals(Constants.BROADCAST_IS_SLEEPCOUNT)) {
				MobileUtil.shutOffScreen(mContext);

				SensorUtil mSensorUtil = new SensorUtil(mContext);
				mSensorUtil.setOnShakeListener(new OnShakeListener() {

					@Override
					public void onShake(int type, double speed) {

						if (type == 1) {
							mTextView.setText("检测到振动");

						} else if (type == 2) {
							mTextView.setText("振动停止");
						}

					}
				});
				// mSensorUtil.start();
			}
		}
	}

}
