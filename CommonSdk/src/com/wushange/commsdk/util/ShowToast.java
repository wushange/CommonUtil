package com.wushange.commsdk.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ShowToast {

	private ShowToast() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isShow = true;
	private static Toast mToast;

	public static void showToast(Context context, CharSequence text) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public static void EmptyToast(Context context) {
		if (mToast == null) {
			mToast = Toast.makeText(context, "不允许为空！", Toast.LENGTH_SHORT);
		} else {
			mToast.setText("不允许为空！");
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public static void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}

	public static void showToastInCenter(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();

	}

}