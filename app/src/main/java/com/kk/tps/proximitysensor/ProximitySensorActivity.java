package com.kk.tps.proximitysensor;

import java.util.Timer;
import java.util.TimerTask;

import com.common.pos.api.util.posutil.TPS980PosUtil;
import com.kk.tps.R;
import com.kk.tps.util.ShellUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class ProximitySensorActivity extends Activity {

	private static TextView show_proximity_sensor;
	private Timer mTimer;
	private TimerTask mTimerTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proximity_sensor);
		initUI();
	}

	private void initUI() {
		show_proximity_sensor = (TextView) findViewById(R.id.show_proximity_sensor);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mTimerTask == null) {
			mTimerTask = new TimerTask() {

				@Override
				public void run() {
					int ret = TPS980PosUtil.getPriximitySensorStatus();
					Log.e("ret---------->", "ret ----" + ret);
					Message mMessage = mHandler.obtainMessage(ret);
					mHandler.sendMessage(mMessage);
				}
			};
		}
		if (mTimer == null) {
			mTimer = new Timer();
		}
		mTimer.schedule(mTimerTask, 0, 1000);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				show_proximity_sensor.setText(R.string.tv_proximity_sensor_none);
				// startActivity(new
				// Intent("android.media.action.IMAGE_CAPTURE"));
				if (android.os.Build.MODEL.equals("TPS980")) {
					ShellUtils.execCommand("echo " + 0 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
				} else {
					ShellUtils.execCommand("echo " + 0 + " >/sys/class/backlight/led-brightness/brightness", false);// usb

				}

				break;
			case 1:
				show_proximity_sensor.setText(R.string.tv_proximity_sensor_someone);
				if (android.os.Build.MODEL.equals("TPS980")) {
					ShellUtils.execCommand("echo " + 50 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
				} else {
					ShellUtils.execCommand("echo " + 100 + " >/sys/class/backlight/led-brightness/brightness", false);// usb

				}
				break;
			default:
				show_proximity_sensor.setText(R.string.tv_proximity_sensor_error);
				if (android.os.Build.MODEL.equals("TPS980")) {
					ShellUtils.execCommand("echo " + 0 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
				} else {
					ShellUtils.execCommand("echo " + 0 + " >/sys/class/backlight/led-brightness/brightness", false);// usb

				}
				break;
			}
		}
	};

	public static void setText(String str) {
		show_proximity_sensor.setText(str);
	}
}
