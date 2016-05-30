package com.wushange.commsdk.customview.tip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wushange.commsdk.R;

public class ShowTipsView extends RelativeLayout {
	private Point showhintPoints;
	private int radius = 0;

	private String title, description, button_text;
	private boolean custom, displayOneTime;
	private int displayOneTimeID = 0;
	private int delay = 0;

	private ShowTipsViewInterface callback;

	private View targetView;
	private int screenX, screenY;

	private int title_color, description_color, background_color, circleColor,
			buttonColor, buttonTextColor;
	private Drawable closeButtonDrawableBG;

	private int background_alpha = 220;

	private StoreUtils showTipsStore;

	private Bitmap bitmap;
	private Canvas temp;
	private Paint paint;
	private Paint bitmapPaint;
	private Paint circleline;
	private Paint transparentPaint;
	private PorterDuffXfermode porterDuffXfermode;

	public ShowTipsView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public ShowTipsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ShowTipsView(Context context) {
		super(context);
		init();
	}

	private void init() {
		this.setVisibility(View.GONE);
		this.setBackgroundColor(Color.TRANSPARENT);

		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// DO NOTHING
				// HACK TO BLOCK CLICKS

			}
		});

		showTipsStore = new StoreUtils(getContext());

		paint = new Paint();
		bitmapPaint = new Paint();
		circleline = new Paint();
		transparentPaint = new Paint();
		porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenX = w;
		screenY = h;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (bitmap == null) {
			bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(),
					Bitmap.Config.ARGB_8888);
			temp = new Canvas(bitmap);
		}

		if (background_color != 0)
			paint.setColor(background_color);
		else
			paint.setColor(Color.parseColor("#000000"));

		paint.setAlpha(background_alpha);
		temp.drawRect(0, 0, temp.getWidth(), temp.getHeight(), paint);

		transparentPaint.setColor(getResources().getColor(
				android.R.color.transparent));
		transparentPaint.setXfermode(porterDuffXfermode);

		int x = showhintPoints.x;
		int y = showhintPoints.y;
		temp.drawCircle(x, y, radius, transparentPaint);

		canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);

		circleline.setStyle(Paint.Style.STROKE);
		if (circleColor != 0)
			circleline.setColor(circleColor);
		else
			circleline.setColor(Color.RED);

		circleline.setAntiAlias(true);
		circleline.setStrokeWidth(3);
		canvas.drawCircle(x, y, radius, circleline);
	}

	boolean isMeasured;

	public void show(final Context activity) {
		if (isDisplayOneTime() && showTipsStore.hasShown(getDisplayOneTimeID())) {
			setVisibility(View.GONE);
			((ViewGroup) ((Activity) getContext()).getWindow().getDecorView())
					.removeView(ShowTipsView.this);
			return;
		} else {
			if (isDisplayOneTime())
				showTipsStore.storeShownId(getDisplayOneTimeID());
		}

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				((ViewGroup) ((Activity) activity).getWindow().getDecorView())
						.addView(ShowTipsView.this);

				ShowTipsView.this.setVisibility(View.VISIBLE);
				Animation fadeInAnimation = AnimationUtils.loadAnimation(
						getContext(), R.anim.fade_in);
				ShowTipsView.this.startAnimation(fadeInAnimation);

				final ViewTreeObserver observer = targetView
						.getViewTreeObserver();
				observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {

						if (isMeasured)
							return;

						if (targetView.getHeight() > 0
								&& targetView.getWidth() > 0) {
							isMeasured = true;

						}

						if (custom == false) {
							int[] location = new int[2];
							targetView.getLocationInWindow(location);
							int x = location[0] + targetView.getWidth() / 2;
							int y = location[1] + targetView.getHeight() / 2;
							// Log.d("FRED", "X:" + x + " Y: " + y);

							Point p = new Point(x, y);

							showhintPoints = p;
							radius = targetView.getWidth() / 2;
						} else {
							int[] location = new int[2];
							targetView.getLocationInWindow(location);
							int x = location[0] + showhintPoints.x;
							int y = location[1] + showhintPoints.y;
							// Log.d("FRED", "X:" + x + " Y: " + y);

							Point p = new Point(x, y);

							showhintPoints = p;

						}

						invalidate();

						createViews();

					}
				});
			}
		}, getDelay());
	}

	/*
	 * Create text views and close button
	 */
	@SuppressLint("NewApi")
	private void createViews() {
		this.removeAllViews();

		RelativeLayout texts_layout = new RelativeLayout(getContext());

		LayoutParams params = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		/*
		 * Title
		 */
		TextView textTitle = new TextView(getContext());
		textTitle.setText(getTitle());
		if (getTitle_color() != 0)
			textTitle.setTextColor(getTitle_color());
		else
			textTitle.setTextColor(getResources().getColor(
					android.R.color.holo_blue_bright));
		textTitle.setId(123);
		textTitle.setTextSize(26);

		// Add title to this view
		texts_layout.addView(textTitle);

		/*
		 * Description
		 */
		TextView text = new TextView(getContext());
		text.setText(getDescription());
		if (getDescription_color() != 0)
			text.setTextColor(getDescription_color());
		else
			text.setTextColor(Color.WHITE);
		text.setTextSize(17);
		params = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.BELOW, 123);
		text.setLayoutParams(params);

		texts_layout.addView(text);

		params = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		LayoutParams paramsTexts = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

		if (screenY / 2 > showhintPoints.y) {
			// textBlock under the highlight circle
			paramsTexts.height = (showhintPoints.y + radius) - screenY;
			paramsTexts.topMargin = (showhintPoints.y + radius);
			texts_layout.setGravity(Gravity.START | Gravity.TOP);

			texts_layout.setPadding(50, 50, 50, 50);
		} else {
			// textBlock above the highlight circle
			paramsTexts.height = showhintPoints.y - radius;

			texts_layout.setGravity(Gravity.START | Gravity.BOTTOM);

			texts_layout.setPadding(50, 100, 50, 50);
		}

		texts_layout.setLayoutParams(paramsTexts);
		this.addView(texts_layout);

		/*
		 * Close button
		 */
		Button btn_close = new Button(getContext());
		btn_close.setId(4375);

		btn_close.setText(getButtonText());
		btn_close.setTextColor(buttonTextColor == 0 ? Color.WHITE
				: buttonTextColor);

		btn_close.setBackground(null);
		if (closeButtonDrawableBG != null) {
			btn_close.setBackground(closeButtonDrawableBG);
		}

		if (buttonColor != 0) {
			btn_close.getBackground().setColorFilter(buttonColor,
					PorterDuff.Mode.MULTIPLY);
		}

		btn_close.setTextSize(17);
		btn_close.setGravity(Gravity.CENTER);

		params = new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params.rightMargin = 20;
		params.bottomMargin = 20;

		btn_close.setLayoutParams(params);
		btn_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getCallback() != null)
					getCallback().gotItClicked();

				setVisibility(View.GONE);
				((ViewGroup) ((Activity) getContext()).getWindow()
						.getDecorView()).removeView(ShowTipsView.this);

			}
		});

		ShowTipsView.this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (getCallback() != null)
					getCallback().gotItClicked();

				setVisibility(View.GONE);
				((ViewGroup) ((Activity) getContext()).getWindow()
						.getDecorView()).removeView(ShowTipsView.this);

			}
		});
		this.addView(btn_close);
	}

	public void setButtonText(String text) {
		this.button_text = text;
	}

	public String getButtonText() {
		if (button_text == null || button_text.equals(""))
			return "跳过>>";

		return button_text;
	}

	public void setTarget(View v) {
		targetView = v;
	}

	public void setTarget(View v, int x, int y, int radius) {
		custom = true;
		targetView = v;
		Point p = new Point(x, y);
		showhintPoints = p;
		this.radius = radius;
	}

	static Point getShowcasePointFromView(View view) {
		Point result = new Point();
		result.x = view.getLeft() + view.getWidth() / 2;
		result.y = view.getTop() + view.getHeight() / 2;
		return result;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDisplayOneTime() {
		return displayOneTime;
	}

	public void setDisplayOneTime(boolean displayOneTime) {
		this.displayOneTime = displayOneTime;
	}

	public ShowTipsViewInterface getCallback() {
		return callback;
	}

	public void setCallback(ShowTipsViewInterface callback) {
		this.callback = callback;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getDisplayOneTimeID() {
		return displayOneTimeID;
	}

	public void setDisplayOneTimeID(int displayOneTimeID) {
		this.displayOneTimeID = displayOneTimeID;
	}

	public int getTitle_color() {
		return title_color;
	}

	public void setTitle_color(int title_color) {
		this.title_color = title_color;
	}

	public int getDescription_color() {
		return description_color;
	}

	public void setDescription_color(int description_color) {
		this.description_color = description_color;
	}

	public int getBackground_color() {
		return background_color;
	}

	public void setBackground_color(int background_color) {
		this.background_color = background_color;
	}

	public int getCircleColor() {
		return circleColor;
	}

	public void setCircleColor(int circleColor) {
		this.circleColor = circleColor;
	}

	public int getBackground_alpha() {
		return background_alpha;
	}

	public void setBackground_alpha(int background_alpha) {
		if (background_alpha > 255)
			this.background_alpha = 255;
		else if (background_alpha < 0)
			this.background_alpha = 0;
		else
			this.background_alpha = background_alpha;

	}

	public int getButtonColor() {
		return buttonColor;
	}

	public void setButtonColor(int buttonColor) {
		this.buttonColor = buttonColor;
	}

	public int getButtonTextColor() {
		return buttonTextColor;
	}

	public void setButtonTextColor(int buttonTextColor) {
		this.buttonTextColor = buttonTextColor;
	}

	public Drawable getCloseButtonDrawableBG() {
		return closeButtonDrawableBG;
	}

	public void setCloseButtonDrawableBG(Drawable closeButtonDrawableBG) {
		this.closeButtonDrawableBG = closeButtonDrawableBG;
	}
}
