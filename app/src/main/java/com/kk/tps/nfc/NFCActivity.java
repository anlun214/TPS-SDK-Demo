package com.kk.tps.nfc;

import com.common.pos.api.util.posutil.TPS980PosUtil;
import com.kk.tps.R;
import com.kk.tps.util.Utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NFCActivity extends Activity implements OnClickListener {

	private TextView show_nfc_message;
	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	private Button send_wg26, send_wg32, send_wg34;
	private String mIDString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfc);
		initUI();
		NfcManager mNfcManager = (NfcManager) getSystemService(Context.NFC_SERVICE);
		mNfcAdapter = mNfcManager.getDefaultAdapter();
		if (mNfcAdapter == null) {
			show_nfc_message.setText(R.string.tv_nfc_notsupport);
		} else if ((mNfcAdapter != null) && (!mNfcAdapter.isEnabled())) {
			show_nfc_message.setText(R.string.tv_nfc_notwork);
		} else if ((mNfcAdapter != null) && (mNfcAdapter.isEnabled())) {
			show_nfc_message.setText(R.string.tv_nfc_working);
		}
		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
		init_NFC();
	}

	private void initUI() {
		show_nfc_message = (TextView) findViewById(R.id.show_nfc_message);
		send_wg26 = (Button) findViewById(R.id.send_wg26);
		send_wg26.setOnClickListener(this);
		send_wg32 = (Button) findViewById(R.id.send_wg32);
		send_wg32.setOnClickListener(this);
		send_wg34 = (Button) findViewById(R.id.send_wg34);
		send_wg34.setOnClickListener(this);

	}

	@Override
	public void onResume() {
		super.onResume();
		Log.e("onResume()--------->", "onResume");
		if (mNfcAdapter != null) {
			mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
			if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(this.getIntent().getAction())) {
				processIntent(this.getIntent());
			}
		}
	}

	@Override
	public void onNewIntent(Intent intent) {
		processIntent(intent);
	}

	public void processIntent(Intent intent) {
		String data = null;
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		String[] techList = tag.getTechList();
		byte[] ID = new byte[20];
		data = tag.toString();
		ID = tag.getId();
		String UID = Utils.bytesToHexString(ID);
		// String idString1 = UID.substring(2, UID.length());
		String IDString = bytearray2Str(hexStringToBytes(UID.substring(2, UID.length())), 0, 4, 10);
		// String
		mIDString = IDString;
		data += "\n\nUID:\n" + UID;
		// data += "\n\nID:\n" + IDString;
		data += "\nData format:";
		for (String tech : techList) {
			data += "\n" + tech;
		}
		/*
		 * data += "\nwg26status:-->" +
		 * PosUtil.getWg26Status(Long.parseLong(IDString)) + "\n"; data +=
		 * "wg34status:-->" + PosUtil.getWg34Status(Long.parseLong(IDString)) +
		 * "\n"; data += "wg32status:-->" +
		 * PosUtil.getWg32Status(Long.parseLong(IDString)) + "\n";
		 */
		show_nfc_message.setText(data);
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.e("onPause()--------->", "onPause");
		if (mNfcAdapter != null) {
			stopNFC_Listener();
		}
	}

	private void init_NFC() {
		IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
	}

	private void stopNFC_Listener() {
		mNfcAdapter.disableForegroundDispatch(this);
	}

	private static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	private static String bytearray2Str(byte[] data, int start, int length, int targetLength) {
		long number = 0;
		if (data.length < start + length) {
			return "";
		}
		for (int i = 1; i <= length; i++) {
			number *= 0x100;
			number += (data[start + length - i] & 0xFF);
		}
		return String.format("%0" + targetLength + "d", number);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.send_wg26:
			if (mIDString != null) {
				Toast.makeText(NFCActivity.this,
						"send wg26 ret : " + TPS980PosUtil.getWg26Status(Long.parseLong(mIDString)), Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.send_wg32:
			if (mIDString != null) {
				Toast.makeText(NFCActivity.this,
						"send wg32 ret : " + TPS980PosUtil.getWg32Status(Long.parseLong(mIDString)), Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.send_wg34:
			if (mIDString != null) {
				Toast.makeText(NFCActivity.this,
						"send wg34 ret : " + TPS980PosUtil.getWg34Status(Long.parseLong(mIDString)), Toast.LENGTH_SHORT)
						.show();
			}
			break;
		}
	}

}
