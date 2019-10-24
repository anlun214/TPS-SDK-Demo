package com.kk.tps.colorled;

import com.common.pos.api.util.posutil.TPS980PosUtil;
import com.kk.tps.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ColorLedActivity extends Activity implements OnClickListener {
	private Button led_red_open, led_red_close, led_white_open, led_white_close, led_green_open, led_green_close;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_colorled);
		initUI();
	}

	private void initUI() {
		led_red_open = (Button) findViewById(R.id.led_red_open);
		led_red_open.setOnClickListener(this);
		led_red_close = (Button) findViewById(R.id.led_red_close);
		led_red_close.setOnClickListener(this);

		led_white_open = (Button) findViewById(R.id.led_white_open);
		led_white_open.setOnClickListener(this);
		led_white_close = (Button) findViewById(R.id.led_white_close);
		led_white_close.setOnClickListener(this);

		led_green_open = (Button) findViewById(R.id.led_green_open);
		led_green_open.setOnClickListener(this);
		led_green_close = (Button) findViewById(R.id.led_green_close);
		led_green_close.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int ret = 0;
		switch (v.getId()) {
		case R.id.led_red_open:
			ret = TPS980PosUtil.setColorLed(1, 61);
			Toast.makeText(ColorLedActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;
		case R.id.led_red_close:
			ret = TPS980PosUtil.setColorLed(0, 61);
			Toast.makeText(ColorLedActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;

		case R.id.led_white_open:
			ret = TPS980PosUtil.setColorLed(1, 60);
			Toast.makeText(ColorLedActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;
		case R.id.led_white_close:
			ret = TPS980PosUtil.setColorLed(0, 60);
			Toast.makeText(ColorLedActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;

		case R.id.led_green_open:
			ret = TPS980PosUtil.setColorLed(1, 62);
			Toast.makeText(ColorLedActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;
		case R.id.led_green_close:
			ret = TPS980PosUtil.setColorLed(0, 62);
			Toast.makeText(ColorLedActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

}
