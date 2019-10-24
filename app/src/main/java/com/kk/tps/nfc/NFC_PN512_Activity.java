package com.kk.tps.nfc;

import com.kk.tps.R;
import com.kk.tps.util.Utils;
import com.telpo.tps550.api.nfc.PN512;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class NFC_PN512_Activity extends Activity {

	TextView et_card_info;
	long readCardNum;
	boolean isReadingNFC;
	private String mIDString;
	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;

	private TextView show_nfc_message;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				new readNFC().execute();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pn512);
		et_card_info = (TextView) findViewById(R.id.tv_card_info);
		show_nfc_message = (TextView) findViewById(R.id.show_nfc_message);

		NfcManager mNfcManager = (NfcManager) getSystemService(Context.NFC_SERVICE);
		mNfcAdapter = mNfcManager.getDefaultAdapter();
		if (mNfcAdapter == null) {
			show_nfc_message.setText(R.string.tv_nfc_notsupport);
		} else if ((mNfcAdapter != null) && (!mNfcAdapter.isEnabled())) {
			show_nfc_message.setText(R.string.tv_nfc_notwork);
		} else if ((mNfcAdapter != null) && (mNfcAdapter.isEnabled())) {
			show_nfc_message.setText(R.string.tv_nfc_working);
		}
		init_NFC();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		isReadingNFC = true;
		new readNFC().execute();
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

	public void processIntent(Intent intent) {
		String data = null;
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		String[] techList = tag.getTechList();
		byte[] ID = new byte[20];
		data = tag.toString();
		ID = tag.getId();
		String UID = Utils.bytesToHexString(ID);
		String IDString = bytearray2Str(hexStringToBytes(UID.substring(2, UID.length())), 0, 4, 10);
		mIDString = IDString;
		data += "\n\nUID:\n" + UID;
		data += "\n\nID:\n" + IDString;
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopNFC_Listener();
	}

	private class readNFC extends AsyncTask<Void, Integer, Long> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Long doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return PN512.readnfc();
		}

		@Override
		protected void onPostExecute(Long result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			String num = Long.toHexString(result);
			if (result > 0) {
				et_card_info.setText("" + num);
			} else {
				// cardNum.setText("");
			}
			if (isReadingNFC) {
				handler.sendEmptyMessageDelayed(1, 300);
			}
		}
	}
}
