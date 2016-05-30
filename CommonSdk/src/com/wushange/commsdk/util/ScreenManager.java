package com.wushange.commsdk.util;

import android.app.Activity;
import android.util.Log;

import java.util.Stack;

public class ScreenManager {

	private static final String T = "ScreenManager";
	private static Stack<Activity> activityStack;
	private static ScreenManager instance;
	private String cur_clazz;

	public void setCur_clazz(String cur_clazz) {
		this.cur_clazz = cur_clazz;
	}

	private ScreenManager() {
		Log.i(T, "initialization");
	}

	public static ScreenManager getScreenManager() {
		Log.i(T, "get instance");
		if (instance == null) {
			instance = new ScreenManager();
		}
		return instance;
	}

	public void popActivity() {
		Log.i(T, "pop activity");
		Activity activity = activityStack.lastElement();
		if (activity != null) {
			activity.finish();
			activity = null;
		}
	}

	public void popActivity(Activity activity) {
		Log.i(T, "pop point activity");
		if (activity != null) {
			activity.finish();
			try {
				activityStack.remove(activity);
				activity = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void removeActivity(Activity activity) {

		Log.i(T, "pop point activity");
		if (activity != null) {
			try {
				activityStack.remove(activity);
				activity = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public Activity currentActivity() {
		Log.i(T, "get current activity");
		try {
			Activity activity = activityStack.lastElement();
			return activity;
		} catch (Exception e) {
			return null;
		}
	}

	public void pushActivity(Activity activity) {
		Log.i(T, "push a activity");
		if (activityStack == null) {
			// Log.i(getClass().getName(),Runtime.getRuntime().totalMemory());
			// Runtime.getRuntime().totalMemory();
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	public void popAllActivityException(Class<?> cls) {
		Log.i(T, "pop all point activity class equal" + cls.getName());
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				if (activityStack.size() == 1) {
					popActivity(activity);
					break;
				}
			}
			popActivity(activity);
		}
	}

	public void popAllActivities() {
		if (null != activityStack) {
			for (Activity activity : activityStack) {
				Log.d("popAllActivities-->", activity.getClass().getName());
				activity.finish();
			}
		}
	}
	public void popOtherActivities() {
		if (null != activityStack) {
			for (Activity activity : activityStack) {
				if (!activity.getClass().getName()
						.equals(currentActivity().getClass().getName())) {
					Log.d("popAllActivities-->", activity.getClass().getName());
					activity.finish();
				}
			}

		}
	}
}