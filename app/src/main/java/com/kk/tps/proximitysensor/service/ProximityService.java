package com.kk.tps.proximitysensor.service;

import java.util.Timer;
import java.util.TimerTask;

import com.common.pos.api.util.posutil.TPS980PosUtil;
import com.kk.tps.proximitysensor.ProximitySensorActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class ProximityService extends Service {

	private Timer mTimer = null;
	private TimerTask mTask = null;
	private static final String TAG = "WINDOW";

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
		if (mTask == null) {
			Log.i(TAG, "chuangjian mTask");
			mTask = new TimerTask() {
				@Override
				public void run() {
					int ret = TPS980PosUtil.getPriximitySensorStatus();
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
		mTimer.schedule(mTask, 0, 300);
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				/*
				 * Process p = null; String cmd =
				 * "echo 255 > /sys/class/backlight/rk28_bl/brightness"; try { p
				 * = Runtime.getRuntime().exec("su"); DataOutputStream os = new
				 * DataOutputStream(p.getOutputStream()); os.writeBytes(cmd +
				 * "\n"); os.writeBytes("exit\n"); os.flush(); p.waitFor(); }
				 * catch (IOException e) { e.printStackTrace(); } catch
				 * (InterruptedException e) { e.printStackTrace(); }
				 */
				ProximitySensorActivity.setText("����");

				// PosUtil.setLedPower(1);
				break;
			case 0:
				/*
				 * Process p1 = null; String cmd1 =
				 * "echo 0 > /sys/class/backlight/rk28_bl/brightness"; try { p1
				 * = Runtime.getRuntime().exec("su"); DataOutputStream os1 = new
				 * DataOutputStream(p1.getOutputStream()); os1.writeBytes(cmd1 +
				 * "\n"); os1.writeBytes("exit\n"); os1.flush(); p1.waitFor(); }
				 * catch (IOException e) { e.printStackTrace(); } catch
				 * (InterruptedException e) { e.printStackTrace(); }
				 */

				ProximitySensorActivity.setText("����");
				// Toast.makeText(getApplicationContext(), "??",
				// Toast.LENGTH_SHORT).show();
				// PosUtil.setLedPower(0);
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
