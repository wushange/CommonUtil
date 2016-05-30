package com.wushange.commsdk.customview.tip;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

public class ShowTipsBuilder {
	ShowTipsView showtipsView;

	public ShowTipsBuilder(Context activity) {
		this.showtipsView = new ShowTipsView(activity);
	}
	public ShowTipsBuilder setTarget(View v) {
		this.showtipsView.setTarget(v);
		return this;
	}

	public ShowTipsBuilder setTarget(View v, int x, int y, int radius) {
		showtipsView.setTarget(v, x, y, radius);

		return this;
	}

	public ShowTipsView build() {
		return showtipsView;
	}

	public ShowTipsBuilder setTitle(String text) {
		this.showtipsView.setTitle(text);
		return this;
	}

	public ShowTipsBuilder setDescription(String text) {
		this.showtipsView.setDescription(text);
		return this;
	}

	public ShowTipsBuilder displayOneTime(int showtipId) {
		this.showtipsView.setDisplayOneTime(true);
		this.showtipsView.setDisplayOneTimeID(showtipId);
		return this;
	}

	public ShowTipsBuilder setCallback(ShowTipsViewInterface callback) {
		this.showtipsView.setCallback(callback);
		return this;
	}

	public ShowTipsBuilder setDelay(int delay) {
		showtipsView.setDelay(delay);
		return this;
	}

	public ShowTipsBuilder setTitleColor(int color) {
		showtipsView.setTitle_color(color);
		return this;
	}

	public ShowTipsBuilder setDescriptionColor(int color) {
		showtipsView.setDescription_color(color);
		return this;
	}

	public ShowTipsBuilder setBackgroundColor(int color) {
		showtipsView.setBackground_color(color);
		return this;
	}

	public ShowTipsBuilder setCircleColor(int color) {
		showtipsView.setCircleColor(color);
		return this;
	}

	public ShowTipsBuilder setButtonText(String text) {
		this.showtipsView.setButtonText(text);
		return this;
	}

	public ShowTipsBuilder setCloseButtonColor(int color) {
		this.showtipsView.setButtonColor(color);
		return this;
	}

	public ShowTipsBuilder setCloseButtonTextColor(int color) {
		this.showtipsView.setButtonTextColor(color);
		return this;
	}

	public ShowTipsBuilder setButtonBackground(Drawable drawable) {
		this.showtipsView.setCloseButtonDrawableBG(drawable);
		return this;
	}

	public ShowTipsBuilder setBackgroundAlpha(int alpha) {
		this.showtipsView.setBackground_alpha(alpha);
		return this;
	}
}
