package com.james.Test;

import com.james.Test.R;
import com.james.Test.demo.WebMusicActivity;
import com.james.Test.demo.ShutOffScreenActivity;
import com.james.Test.demo.IflyLinstenActivity;
import com.james.Test.view.FeatureView;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 
 * @author James
 * @Package com.james.Test
 * @Title: MainActivity.java
 * @Description James-demo
 * @date 2015-8-31
 * @version 1.0
 * 
 */
public class MainActivity extends ListActivity {

	// 所有demoe数组
	static DemoDetails[] demos = {
			new DemoDetails("ShutOffScreenActivity",
					R.string.james_test_shutoffscreen,
					ShutOffScreenActivity.class),
			new DemoDetails("MyWebActivity", R.string.james_test_onlinemusic,
					WebMusicActivity.class),
			new DemoDetails("IflyLinstenActivity", R.string.james_test_ifly,
					IflyLinstenActivity.class) };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListAdapter adapter = new CustomArrayAdapter(
				this.getApplicationContext(), demos);
		setListAdapter(adapter);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.exit(0);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
		Intent mIntent = new Intent(this.getApplicationContext(),
				demo.activityClass);
		if (position == 1) {
			mIntent.putExtra(
					"webUrl",
					"http://box.baidu.com/widget/flash/song.swf?name=好日子&artist=宋祖英&autoPlay=true&qq-pf-to=pcqq.c2c");
		}
		startActivity(mIntent);
	}

	/**
	 * 
	 * @author James
	 * @Package com.james.Test
	 * @Title: MainActivity.java
	 * @Description adapter
	 * @date 2015-8-31
	 * @version 1.0
	 * 
	 */
	private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
		public CustomArrayAdapter(Context context, DemoDetails[] demos) {
			super(context, R.layout.feature, R.id.title, demos);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			FeatureView featureView;// 自定义fragment视图
			if (convertView instanceof FeatureView) {
				featureView = (FeatureView) convertView;
			} else {
				featureView = new FeatureView(getContext());
			}
			DemoDetails demo = getItem(position);
			featureView.setTitleId(demo.activityName);
			featureView.setDescriptionId(demo.descriptionId);
			return featureView;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
