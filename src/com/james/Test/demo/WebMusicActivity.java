package com.james.Test.demo;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.james.Test.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebMusicActivity extends Activity {
	private ProgressBar pb_webview;
	private WebView webView;
	private View llayData;
	private TextView errTextView;

	private String url = "";
	private ArrayList<String> urlList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_myweb);
		url = getIntent().getStringExtra("webUrl");

		initViews();
		setWebviewData();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void initViews() {
		pb_webview = (ProgressBar) findViewById(R.id.pb_webview);
		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.requestFocus();
		llayData = findViewById(R.id.llayData);
		errTextView = (TextView) findViewById(R.id.errTextView);
	}

	/**
	 * 显示WebView
	 * 
	 * @return: void
	 */
	private void setWebviewData() {
		llayData.setVisibility(View.VISIBLE);
		errTextView.setVisibility(View.GONE);

		// 更新加载进度条
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				pb_webview.setProgress(progress);
				if (progress == 100) {
					pb_webview.setVisibility(View.GONE);
				}
			}
		});

		webView.setWebViewClient(myWebViewClient);
		webView.loadUrl(url);
	}

	/**
	 * 重写WebViewClient
	 */
	WebViewClient myWebViewClient = new WebViewClient() {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			pb_webview.setVisibility(View.VISIBLE);
			final String urlConnect = url;
			if (!urlList.contains(url)) {
				urlList.add(url);
			}

			new Thread(new Runnable() {
				@Override
				public void run() {
					int statusCode = getStatusCode(urlConnect);
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putInt("validResponse", statusCode);
					msg.setData(bundle);
					mHandler.sendMessage(msg);
				}
			}).start();
			dealwithErrPage(false);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			String data = "";
			view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
		}
	};

	private void backWebiew() {
		webView.goBack();

		int index = urlList.size();
		if (index > 0) {
			urlList.remove(index - 1);
		}

		if (urlList.size() > 0) {

		} else {
			finish();
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			int validResponse = bundle.getInt("validResponse");
			if (validResponse == 200) {
				dealwithErrPage(false);
			} else if (validResponse == 401) {
				String data = "";
				webView.loadUrl("javascript:document.body.innerHTML=\"" + data
						+ "\"");
			} else {
				dealwithErrPage(true);
			}
			super.handleMessage(msg);
		}
	};

	private int getStatusCode(String url) {
		HttpParams httpparams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpparams, 300000);
		HttpConnectionParams.setSoTimeout(httpparams, 300000);
		HttpClient httpClient = new DefaultHttpClient(httpparams);

		HttpGet httpGet = new HttpGet(url);

		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			return statusCode;
		} catch (Exception e) {

		}
		return 0;
	}

	private void dealwithErrPage(boolean isErrPage) {
		if (isErrPage) {
			llayData.setVisibility(View.GONE);
			errTextView.setVisibility(View.VISIBLE);
		} else {
			llayData.setVisibility(View.VISIBLE);
			errTextView.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onPause() {
		WebMusicActivity.this.finish();
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			backWebiew();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}
}
