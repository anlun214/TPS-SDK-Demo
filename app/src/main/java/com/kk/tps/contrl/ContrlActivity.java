package com.kk.tps.contrl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.kk.tps.R;
import com.kk.tps.contrl.service.ContrlService;

public class ContrlActivity extends Activity implements View.OnClickListener {
	Button start_contrl_service = null;
	Button stop_contrl_service = null;
	static TextView show;
	private static Activity activity = null;
	Intent intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contrl);
		activity = ContrlActivity.this;

		initUI();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (intent != null) {
			stopService(intent);
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private void initUI() {
		start_contrl_service = (Button) findViewById(R.id.start_contrl_service);
		stop_contrl_service = (Button) findViewById(R.id.stop_contrl_service);
		start_contrl_service.setOnClickListener(this);
		stop_contrl_service.setOnClickListener(this);

		show = (TextView) findViewById(R.id.show);
	}

	@Override
	public void onClick(View v) {
		intent = new Intent(this, ContrlService.class);
		switch (v.getId()) {
		case R.id.start_contrl_service:
			Log.e("TAG", "onCLick");
			startService(intent);
			break;
		case R.id.stop_contrl_service:
			Log.e("TAG", "onCLick2");
			show.setText("");
			stopService(intent);
		}
	}

	public static void setBrightness(int brightness) {
		if (activity.getWindow() != null) {
			WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
			lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
			activity.getWindow().setAttributes(lp);
			Log.i("WINDOW", "not null");
		}

	}

	public static void show(int msg) {
		show.setTextSize(180);
		show.setText(msg);
	}

}