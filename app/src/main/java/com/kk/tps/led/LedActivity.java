package com.kk.tps.led;

import com.common.pos.api.util.posutil.TPS980PosUtil;
import com.kk.tps.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LedActivity extends Activity implements OnClickListener {

	private Button led_open, led_close;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_led);
		initUI();
	}

	private void initUI() {
		led_open = (Button) findViewById(R.id.led_open);
		led_open.setOnClickListener(this);
		led_close = (Button) findViewById(R.id.led_close);
		led_close.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int ret = 0;
		switch (v.getId()) {
		case R.id.led_open:
			ret = TPS980PosUtil.setLedPower(1);
			Toast.makeText(LedActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;
		case R.id.led_close:
			ret = TPS980PosUtil.setLedPower(0);
			Toast.makeText(LedActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

}
