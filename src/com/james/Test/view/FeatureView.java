package com.james.Test.view;

import com.james.Test.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 
 * @author James
 * @Package com.james.Test.view
 * @Title: FeatureView.java
 * @Description 自定义FrameLayout
 * @date 2015-8-31
 * @version 1.0
 * 
 */
public final class FeatureView extends FrameLayout {

	public FeatureView(Context context) {
		super(context);

		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.feature, this);
	}

	public synchronized void setTitleId(String titleId) {
		((TextView) (findViewById(R.id.title)))
				.setText(String.valueOf(titleId));
	}

	public synchronized void setDescriptionId(int descriptionId) {
		((TextView) (findViewById(R.id.description))).setText(descriptionId);
	}

}
