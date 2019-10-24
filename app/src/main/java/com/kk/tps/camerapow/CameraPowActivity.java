package com.kk.tps.camerapow;

import com.common.pos.api.util.posutil.TPS980PosUtil;
import com.kk.tps.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CameraPowActivity extends Activity implements OnClickListener {

	private Button camera0_close, camera0_open, camera1_close, camera1_open;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camerapow);
		initUI();
	}

	private void initUI() {
		camera0_close = (Button) findViewById(R.id.camera0_close);
		camera0_close.setOnClickListener(this);
		camera0_open = (Button) findViewById(R.id.camera0_open);
		camera0_open.setOnClickListener(this);

		camera1_close = (Button) findViewById(R.id.camera1_close);
		camera1_close.setOnClickListener(this);
		camera1_open = (Button) findViewById(R.id.camera1_open);
		camera1_open.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int ret = 0;
		switch (v.getId()) {
		case R.id.camera0_close:
			ret = TPS980PosUtil.setCameraPower(0, 0);
			Toast.makeText(CameraPowActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;
		case R.id.camera0_open:
			ret = TPS980PosUtil.setCameraPower(0, 1);
			Toast.makeText(CameraPowActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;
		case R.id.camera1_close:
			ret = TPS980PosUtil.setCameraPower(1, 0);
			Toast.makeText(CameraPowActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;
		case R.id.camera1_open:
			ret = TPS980PosUtil.setCameraPower(1, 1);
			Toast.makeText(CameraPowActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

}
