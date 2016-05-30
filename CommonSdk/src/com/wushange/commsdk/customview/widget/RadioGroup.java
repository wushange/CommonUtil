/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * 2013.11.06 updated by xiexiaojian 
 * Support that LinearLayouts or RadioButtons can be all RadioGroup's childrens,
 * we can display more rows and columns at the same time.
 */

package com.wushange.commsdk.customview.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class RadioGroup extends LinearLayout {
	private int mCheckedId = -1;
	private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
	private boolean mProtectFromCheckedChange = false;
	private OnCheckedChangeListener mOnCheckedChangeListener;
	private PassThroughHierarchyChangeListener mPassThroughListener;

	public RadioGroup(Context context) {
		super(context);
		setOrientation(VERTICAL);
		init();
	}

	public RadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		mCheckedId = View.NO_ID;

		final int index = VERTICAL;
		setOrientation(index);

		init();
	}

	private void init() {
		mChildOnCheckedChangeListener = new CheckedStateTracker();
		mPassThroughListener = new PassThroughHierarchyChangeListener();
		super.setOnHierarchyChangeListener(mPassThroughListener);
	}

	@Override
	public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
		// the user listener is delegated to our pass-through listener
		mPassThroughListener.mOnHierarchyChangeListener = listener;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		if (mCheckedId != -1) {
			mProtectFromCheckedChange = true;
			setCheckedStateForView(mCheckedId, true);
			mProtectFromCheckedChange = false;
			setCheckedId(mCheckedId);
		}
	}

	@Override
	public void addView(final View child, int index,
			ViewGroup.LayoutParams params) {
		if (child instanceof RadioButton) {

			((RadioButton) child).setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					((RadioButton) child).setChecked(true);
					checkRadioButton((RadioButton) child);
					if (mOnCheckedChangeListener != null) {
						mOnCheckedChangeListener.onCheckedChanged(
								RadioGroup.this, child.getId());
					}
					return true;
				}
			});

		} else if (child instanceof LinearLayout) {
			int childCount = ((LinearLayout) child).getChildCount();
			for (int i = 0; i < childCount; i++) {
				View view = ((LinearLayout) child).getChildAt(i);
				if (view instanceof RadioButton) {
					final RadioButton button = (RadioButton) view;

					button.setOnTouchListener(new OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {
							button.setChecked(true);
							checkRadioButton(button);
							if (mOnCheckedChangeListener != null) {
								mOnCheckedChangeListener.onCheckedChanged(
										RadioGroup.this, button.getId());
							}
							return true;
						}
					});

				}
			}
		}

		super.addView(child, index, params);
	}

	private void checkRadioButton(RadioButton radioButton) {
		View child;
		int radioCount = getChildCount();
		for (int i = 0; i < radioCount; i++) {
			child = getChildAt(i);
			if (child instanceof RadioButton) {
				if (child == radioButton) {
					// do nothing
				} else {
					((RadioButton) child).setChecked(false);
				}
			} else if (child instanceof LinearLayout) {
				int childCount = ((LinearLayout) child).getChildCount();
				for (int j = 0; j < childCount; j++) {
					View view = ((LinearLayout) child).getChildAt(j);
					if (view instanceof RadioButton) {
						final RadioButton button = (RadioButton) view;
						if (button == radioButton) {
							// do nothing
						} else {
							button.setChecked(false);
						}
					}
				}
			}
		}
	}

	public void check(int id) {
		if (id != -1 && (id == mCheckedId)) {
			return;
		}

		if (mCheckedId != -1) {
			setCheckedStateForView(mCheckedId, false);
		}

		if (id != -1) {
			setCheckedStateForView(id, true);
		}

		setCheckedId(id);
	}

	private void setCheckedId(int id) {
		mCheckedId = id;
	}

	private void setCheckedStateForView(int viewId, boolean checked) {
		View checkedView = findViewById(viewId);
		if (checkedView != null && checkedView instanceof RadioButton) {
			((RadioButton) checkedView).setChecked(checked);
		}
	}

	public int getCheckedRadioButtonId() {
		return mCheckedId;
	}

	public void clearCheck() {
		check(-1);
	}

	public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
		mOnCheckedChangeListener = listener;
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new RadioGroup.LayoutParams(getContext(), attrs);
	}

	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		return p instanceof RadioGroup.LayoutParams;
	}

	@Override
	protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
	}

	@Override
	public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
		super.onInitializeAccessibilityEvent(event);
		event.setClassName(RadioGroup.class.getName());
	}

	@SuppressLint("NewApi")
	@Override
	public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
		super.onInitializeAccessibilityNodeInfo(info);
		info.setClassName(RadioGroup.class.getName());
	}

	public static class LayoutParams extends LinearLayout.LayoutParams {
		public LayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);
		}

		public LayoutParams(int w, int h) {
			super(w, h);
		}

		public LayoutParams(int w, int h, float initWeight) {
			super(w, h, initWeight);
		}

		public LayoutParams(ViewGroup.LayoutParams p) {
			super(p);
		}

		public LayoutParams(MarginLayoutParams source) {
			super(source);
		}

		@Override
		protected void setBaseAttributes(TypedArray a, int widthAttr,
				int heightAttr) {

			if (a.hasValue(widthAttr)) {
				width = a.getLayoutDimension(widthAttr, "layout_width");
			} else {
				width = WRAP_CONTENT;
			}

			if (a.hasValue(heightAttr)) {
				height = a.getLayoutDimension(heightAttr, "layout_height");
			} else {
				height = WRAP_CONTENT;
			}
		}
	}

	public interface OnCheckedChangeListener {
		public void onCheckedChanged(RadioGroup group, int checkedId);
	}

	private class CheckedStateTracker implements
			CompoundButton.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (mProtectFromCheckedChange) {
				return;
			}

			mProtectFromCheckedChange = true;
			if (mCheckedId != -1) {
				setCheckedStateForView(mCheckedId, false);
			}
			mProtectFromCheckedChange = false;

			int id = buttonView.getId();
			setCheckedId(id);
		}
	}

	private class PassThroughHierarchyChangeListener implements
			ViewGroup.OnHierarchyChangeListener {
		private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;
		@Override
		public void onChildViewAdded(View parent, View child) {
			if (parent == RadioGroup.this && child instanceof RadioButton) {
				int id = child.getId();
				if (id == View.NO_ID) {
					id = child.hashCode();
					child.setId(id);
				}
				((RadioButton) child)
						.setOnCheckedChangeListener(mChildOnCheckedChangeListener);
			}

			if (mOnHierarchyChangeListener != null) {
				mOnHierarchyChangeListener.onChildViewAdded(parent, child);
			}
		}

		@Override
		public void onChildViewRemoved(View parent, View child) {
			if (parent == RadioGroup.this && child instanceof RadioButton) {
				((RadioButton) child).setOnCheckedChangeListener(null);
			}

			if (mOnHierarchyChangeListener != null) {
				mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
			}
		}
	}
}
