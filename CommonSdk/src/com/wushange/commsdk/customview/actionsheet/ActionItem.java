package com.wushange.commsdk.customview.actionsheet;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ActionItem {
	public Drawable mDrawable;
	public String mTitle;

	public ActionItem(Drawable drawable, String title) {
		this.mDrawable = drawable;
		this.mTitle = title;
	}

	public ActionItem(Context context, int titleId, int drawableId) {
		this.mTitle = (String) context.getResources().getText(titleId);
		this.mDrawable = context.getResources().getDrawable(drawableId);
	}

	public ActionItem(Context context, String titleId) {
		this.mTitle = titleId;
	}

	public ActionItem(Context context, String title, int drawableId) {
		this.mTitle = title;
		this.mDrawable = context.getResources().getDrawable(drawableId);
	}
}
