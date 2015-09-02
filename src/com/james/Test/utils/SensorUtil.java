package com.james.Test.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

/***
 * @author James
 * @Package com.james.Test.utils
 * @Title: SensorUtil.java
 * @Description Sensor振动监听
 * @date 2015-9-1
 * @version 1.0
 * 
 */
public class SensorUtil implements SensorEventListener {

	private SensorManager sensorManager;
	private Sensor sensor;
	private OnShakeListener onShakeListener;
	private Context context;
	private int lastX;
	private int lastY;
	private int lastZ;
	private long lastUpdateTime;
	public boolean b = false;

	public SensorUtil(Context context) {
		this.context = context;
	}

	public void setOnShakeListener(OnShakeListener listener) {
		onShakeListener = listener;
	}

	public void start() {
		sensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager != null) {
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		if (sensor != null) {
			sensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_GAME);
		}
	}

	public void stop() {
		if (sensorManager != null) {
			sensorManager.unregisterListener(this);
			Toast.makeText(context, "ȡ��G-SENSOR", 1000).show();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		if (event == null) {
			return;
		}

		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			long currentUpdateTime = System.currentTimeMillis();
			long timeInterval = currentUpdateTime - lastUpdateTime;

			if (timeInterval < Constants.UPTATE_INTERVAL_TIME) {
				return;
			}


			lastUpdateTime = currentUpdateTime;
			int x = (int) event.values[0];
			int y = (int) event.values[1];
			int z = (int) event.values[2];
			int deltaX = x - lastX;
			int deltaY = y - lastY;
			int deltaZ = z - lastZ;
			// double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY +
			// deltaZ
			// * deltaZ)
			// / timeInterval * 10000;

			int px = Math.abs(deltaX);
			int py = Math.abs(deltaY);
			int pz = Math.abs(deltaZ);
			int p = getMaxValue(px, py, pz);
			if (p >= Constants.SPEED_SHRESHOLD) {
				if (b == false) {
					b = true;
				} else {
					onShakeListener.onShake(1, p);

				}

			} else {
				onShakeListener.onShake(2, py);
			}
			lastX = x;
			lastY = y;
			lastZ = z;
		}

	}

	/**
	 * 
	 * @param px
	 * @param py
	 * @param pz
	 * @return
	 */
	public int getMaxValue(int px, int py, int pz) {
		int max = 0;
		if (px > py && px > pz) {
			max = px;
		} else if (py > px && py > pz) {
			max = py;
		} else if (pz > px && pz > py) {
			max = pz;
		}

		return max;
	}

	// 回调接口
	public interface OnShakeListener {
		/**
		 * 
		 * @param type
		 * @param speed
		 */
		public void onShake(int type, double speed);
	}

}
