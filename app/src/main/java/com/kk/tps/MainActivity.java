package com.kk.tps;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kk.tps.belltest.BellTestActivity;
import com.kk.tps.camerapow.CameraPowActivity;
import com.kk.tps.colorled.ColorLedActivity;
import com.kk.tps.contrl.ContrlActivity;
import com.kk.tps.flushled.FlushLedActivity;
import com.kk.tps.hsjidcard.HSJ160TestActivity;
import com.kk.tps.hsjidcard.HSJ260TestActivity;
import com.kk.tps.led.LedActivity;
import com.kk.tps.led.LightnessActivity;
import com.kk.tps.nfc.NFCActivity;
import com.kk.tps.nfc.NFC_PN512_Activity;
import com.kk.tps.proximitysensor.ProximitySensorActivity;
import com.kk.tps.relay.RelayActivity;
import com.kk.tps.rs485.Rs485Activity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void toFlush_led_title(View view) {
		startActivity(new Intent(MainActivity.this, FlushLedActivity.class));
	}

	public void toProximity_sensor_title(View view) {
		startActivity(new Intent(MainActivity.this, ProximitySensorActivity.class));
	}

	public void toRelay_title(View view) {
		startActivity(new Intent(MainActivity.this, RelayActivity.class));
	}

	public void toRs485_title(View view) {
		startActivity(new Intent(MainActivity.this, Rs485Activity.class));
	}

	public void toNfc_title(View view) {
		if (android.os.Build.MODEL.equals("TPS950")) {
			startActivity(new Intent(MainActivity.this, NFC_PN512_Activity.class));
		} else {
			startActivity(new Intent(MainActivity.this, NFCActivity.class));
		}

	}

	public void toContrl_title(View view) {
		startActivity(new Intent(MainActivity.this, ContrlActivity.class));
	}

	public void toLed_title(View view) {
		startActivity(new Intent(MainActivity.this, LedActivity.class));
	}

	public void toRing(View view) {
		startActivity(new Intent(MainActivity.this, BellTestActivity.class));
	}

	public void toBlue_green_red_led(View view) {
		startActivity(new Intent(MainActivity.this, ColorLedActivity.class));
	}

	public void toLightness(View view) {
		startActivity(new Intent(MainActivity.this, LightnessActivity.class));
	}

	public void toCamera(View view) {
		startActivity(new Intent(MainActivity.this, CameraPowActivity.class));
	}

	public void toHsj160_idcard(View view) {
		startActivity(new Intent(MainActivity.this, HSJ160TestActivity.class));
	}

	public void toHsj260_idcard(View view) {
		startActivity(new Intent(MainActivity.this, HSJ260TestActivity.class));
	}

}
