package com.kk.tps.contrl.service;

import java.util.Timer;
import java.util.TimerTask;

import com.common.pos.api.util.posutil.TPS980PosUtil;
import com.kk.tps.R;
import com.kk.tps.contrl.ContrlActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

public class ContrlService extends Service {

	private Timer mTimer = null;
	private TimerTask mTask = null;
	private static final String TAG = "WINDOW";
	PowerManager pm;
	PowerManager.WakeLock wakeLock;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	Handler showHandler = new Handler(Looper.getMainLooper());

	private void show(final String message) {
		showHandler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

			}

		});
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "service is onCreate");
		/* PowerManager pm = getSystemService(); */
		if (mTask == null) {
			Log.i(TAG, "chuangjian mTask");
			mTask = new TimerTask() {
				@Override
				public void run() {
					int ret = TPS980PosUtil.getPriximitySensorStatus400b(1);
					Message message = mHandler.obtainMessage(ret);
					mHandler.sendMessage(message);
					Log.i(TAG, "schedule is running");
					Log.i(TAG, ret + "");
				}
			};
		}
		if (mTimer == null) {
			Log.i(TAG, "chuangjian mTimer");
			mTimer = new Timer();
		}
		mTimer.schedule(mTask, 0, 1000);
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				// ContrlActivity.setBrightness(255);
				ContrlActivity.show(R.string.remove);
				break;
			case 0:
				Log.i(TAG, "case 0");
				// ContrlActivity.setBrightness(0);
				ContrlActivity.show(R.string.not_remove);
				break;
			default:
				show("error");
				break;
			}
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		mTimer.cancel();
		mTask = null;
		mTimer = null;
		Log.i(TAG, "service is onDestroy");
	}
}
