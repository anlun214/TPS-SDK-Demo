package com.kk.tps.led;

import com.common.pos.api.util.posutil.TPS980PosUtil;
import com.kk.tps.R;
import com.kk.tps.util.ShellUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class LightnessActivity extends Activity {

	Button btn_commit_1, btn_commit_50, btn_commit_100, btn_commit_150, btn_commit_200, btn_commit_255;
	EditText et_inputLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.lightness_activity);
		btn_commit_1 = (Button) findViewById(R.id.btn_lightness_0);
		btn_commit_50 = (Button) findViewById(R.id.btn_lightness_50);
		btn_commit_100 = (Button) findViewById(R.id.btn_lightness_100);
		btn_commit_150 = (Button) findViewById(R.id.btn_lightness_150);
		btn_commit_200 = (Button) findViewById(R.id.btn_lightness_200);
		btn_commit_255 = (Button) findViewById(R.id.btn_lightness_255);
		et_inputLevel = (EditText) findViewById(R.id.et_lightness);
		btn_commit_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (android.os.Build.MODEL.equals("TPS980")) {
					ShellUtils.execCommand("echo " + 0 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
				} else if (android.os.Build.MODEL.equals("TPS980P")) {
					ShellUtils.execCommand("echo " + 0 + ">/sys/class/backlight/brightness/brightness", true);// usb
					ShellUtils.execCommand("echo " + 0 + " >/sys/class/backlight/led-brightness/brightness", true);// usb

				} else {
					TPS980PosUtil.setLedLightness(0);
				}

			}

		});
		btn_commit_50.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (android.os.Build.MODEL.equals("TPS980")) {
					ShellUtils.execCommand("echo " + 50 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
				} else if (android.os.Build.MODEL.equals("TPS980P")) {
					ShellUtils.execCommand("echo " + 50 + ">/sys/class/backlight/brightness/brightness", true);// usb
					ShellUtils.execCommand("echo " + 50 + " >/sys/class/backlight/led-brightness/brightness", true);// usb

				} else {
					TPS980PosUtil.setLedLightness(5);
				}

			}

		});
		btn_commit_255.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (android.os.Build.MODEL.equals("TPS980")) {
					ShellUtils.execCommand("echo " + 255 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
				} else if (android.os.Build.MODEL.equals("TPS980P")) {
					ShellUtils.execCommand("echo " + 255 + ">/sys/class/backlight/brightness/brightness", true);// usb
					ShellUtils.execCommand("echo " + 255 + " >/sys/class/backlight/led-brightness/brightness", true);// usb

				} else {
					TPS980PosUtil.setLedLightness(31);
				}
			}

		});
		btn_commit_100.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (android.os.Build.MODEL.equals("TPS980")) {
					ShellUtils.execCommand("echo " + 100 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
				} else if (android.os.Build.MODEL.equals("TPS980P")) {
					ShellUtils.execCommand("echo " + 100 + ">/sys/class/backlight/brightness/brightness", true);// usb
					ShellUtils.execCommand("echo " + 100 + " >/sys/class/backlight/led-brightness/brightness", true);// usb

				} else {
					TPS980PosUtil.setLedLightness(10);
				}
			}

		});
		btn_commit_150.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (android.os.Build.MODEL.equals("TPS980")) {
					ShellUtils.execCommand("echo " + 150 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
				} else if (android.os.Build.MODEL.equals("TPS980P")) {
					ShellUtils.execCommand("echo " + 150 + ">/sys/class/backlight/brightness/brightness", true);// usb
					ShellUtils.execCommand("echo " + 150 + " >/sys/class/backlight/led-brightness/brightness", true);// usb

				} else {
					TPS980PosUtil.setLedLightness(15);
				}
			}

		});
		btn_commit_200.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (android.os.Build.MODEL.equals("TPS980")) {
					ShellUtils.execCommand("echo " + 200 + " >/sys/class/backlight/rk28_bl_sub/brightness", false);// usb
				} else if (android.os.Build.MODEL.equals("TPS980P")) {
					ShellUtils.execCommand("echo " + 200 + ">/sys/class/backlight/brightness/brightness", true);// usb
					ShellUtils.execCommand("echo " + 200 + " >/sys/class/backlight/led-brightness/brightness", true);// usb

				} else {
					TPS980PosUtil.setLedLightness(25);
				}
			}

		});
	}
}
