package com.kk.tps.belltest;

import com.common.pos.api.util.posutil.TPS980PosUtil;
import com.kk.tps.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BellTestActivity extends Activity implements OnClickListener {

	private Button bell_open, bell_close;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bell);
		initUI();
	}

	private void initUI() {
		bell_open = (Button) findViewById(R.id.bell_open);
		bell_open.setOnClickListener(this);
		bell_close = (Button) findViewById(R.id.bell_close);
		bell_close.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int ret = 0;
		switch (arg0.getId()) {
		case R.id.bell_open:
			ret = TPS980PosUtil.setBell(1);
			Toast.makeText(BellTestActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;
		case R.id.bell_close:
			ret = TPS980PosUtil.setBell(0);
			Toast.makeText(BellTestActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

}
