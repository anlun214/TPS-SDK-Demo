package com.kk.tps.hsjidcard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.kk.tps.R;
import com.telpo.tps550.api.DeviceAlreadyOpenException;
import com.telpo.tps550.api.serial.Serial;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class HSJ260TestActivity extends Activity {

	TextView hsj_result;
	Serial serial;
	InputStream mInputStream = null;
	ReadThread readThread;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				byte[] temp = (byte[]) msg.obj;

				int dataCount = 0;
				for (int i = 1; i < temp.length; i++) {
					Log.e("temp[i]------------>", temp[i] + "");
					// if (temp[i] != (byte) 0x00) {
					// dataCount = i;
					// break;
					// }
				}
				String tempDataString = toHexString(temp);
				String dataString;
				// if (temp[0] == (byte) 0x04) {
				// temp = Arrays.copyOfRange(temp, dataCount, temp.length);
				// tempDataString = toHexString(temp);
				// for (int i = 1; i < temp.length; i++) {
				// if (temp[i] != (byte) 0x00) {
				// dataCount = i;
				// break;
				// }
				// }
				// }
				dataString = tempDataString.substring(2, tempDataString.length());
				// dataString = tempDataString.substring(2*dataCount,
				// tempDataString.length());
				hsj_result.setText("������Ϣ:" + dataString + "\n��ſ�...");
				// hsj_result.setText("������Ϣ:" + Long.parseLong(dataString, 16) +
				// "\n��ſ�...");
				break;
			case 2:
				if (dataBuffer.length() == 22) {
					dataBuffer.setLength(0);
				}
				break;
			default:
				break;
			}
		};
	};
	// "E168B326"

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hsj);
		hsj_result = (TextView) findViewById(R.id.hsj_result);
		dataBuffer = new StringBuffer();

		try {
			try {
				serial = new Serial("/dev/ttyS3", 9600, 0);
			} catch (DeviceAlreadyOpenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mInputStream = serial.getInputStream();
		readThread = new ReadThread();
		readThread.start();
	}

	public void close(View view) {
		try {
			if (readThread != null)
				readThread.interrupt();
			if (mInputStream != null) {
				mInputStream.close();
				mInputStream = null;
			}
		} catch (IOException e) {

		}
		if (serial != null) {
			serial.close();
			serial = null;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			if (readThread != null)
				readThread.interrupt();
			if (mInputStream != null) {
				mInputStream.close();
				mInputStream = null;
			}
		} catch (IOException e) {

		}
		if (serial != null) {
			serial.close();
			serial = null;
		}
	}

	byte[] dataArray;
	StringBuffer dataBuffer;
	long nowReadTime;

	private class ReadThread extends Thread {

		@Override
		public void run() {
			super.run();
			while (!isInterrupted()) {
				int size = 0;
				try {
					byte[] buffer = new byte[64];
					if (mInputStream != null) {
						size = mInputStream.read(buffer);
						dataBuffer.append(toHexString(Arrays.copyOfRange(buffer, 0, size)));
						setLog("databuffer:" + dataBuffer.toString() + ";size:" + dataBuffer.length());
					}

					handler.sendEmptyMessageDelayed(2, 50);

					if (dataBuffer.length() > 23) {
						String tempString = dataBuffer.toString();
						for (int i = 0; (i + 18) < dataBuffer.length(); i++) {
							if (checkXor(tempString.substring(i, i + 18))
									.equals(tempString.substring(i + 18, i + 20))) {
								Message message = new Message();
								message.obj = toBytes(tempString.substring(i, i + 18));
								message.what = 1;
								handler.sendMessage(message);
								// String temp = tempString.substring(i+20,
								// tempString.length());
								dataBuffer.setLength(0);
								// dataBuffer.append(temp);
								break;
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					setLog("cat exception");
				}
			}
		}
	}

	public String checkXor(String data) {
		int checkData = 0;
		for (int i = 0; i < data.length(); i = i + 2) {
			// ��ʮ�������ַ���ת��ʮ����
			int start = Integer.parseInt(data.substring(i, i + 2), 16);
			// �����������
			checkData = start ^ checkData;
		}
		return integerToHexString(checkData);
	}

	public String integerToHexString(int s) {
		String ss = Integer.toHexString(s);
		if (ss.length() % 2 != 0) {
			ss = "0" + ss;// 0F��ʽ
		}
		return ss.toUpperCase();
	}

	public static String toHexString(byte[] data) {
		if (data == null) {
			return "";
		}

		String string;
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < data.length; i++) {
			string = Integer.toHexString(data[i] & 0xFF);
			if (string.length() == 1) {
				stringBuilder.append("0");
			}

			stringBuilder.append(string.toUpperCase());
		}

		return stringBuilder.toString();
	}

	public static byte[] toBytes(String string) {
		int len;
		String str;
		String hexStr = "0123456789ABCDEF";

		String s = string.toUpperCase();

		len = s.length();
		if ((len % 2) == 1) {
			str = s + "0";
			len = (len + 1) >> 1;
		} else {
			str = s;
			len >>= 1;
		}

		byte[] bytes = new byte[len];
		byte high;
		byte low;

		for (int i = 0, j = 0; i < len; i++, j += 2) {
			high = (byte) (hexStr.indexOf(str.charAt(j)) << 4);
			low = (byte) hexStr.indexOf(str.charAt(j + 1));
			bytes[i] = (byte) (high | low);
		}

		return bytes;
	}

	private void setLog(String content) {
		Log.d("HSJ", content);
	}
}
