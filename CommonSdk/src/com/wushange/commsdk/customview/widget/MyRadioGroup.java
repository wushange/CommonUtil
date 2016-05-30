package com.wushange.commsdk.customview.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

public class MyRadioGroup extends LinearLayout {
    private int mCheckedId = -1;
    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
    private boolean mProtectFromCheckedChange = false;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private PassThroughHierarchyChangeListener mPassThroughListener;

    public MyRadioGroup(Context context) {
        super(context);
        setOrientation(VERTICAL);
        init();
    }
    public MyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mChildOnCheckedChangeListener = new CheckedStateTracker();
        mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(mPassThroughListener);
    }

    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        mPassThroughListener.mOnHierarchyChangeListener = listener;
    }
    
    public void setCheckWithoutNotif(int id){
        if (id != -1 && (id == mCheckedId)) {
            return;
        }

        mProtectFromCheckedChange = true;
        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false);
        }

        if (id != -1) {
            setCheckedStateForView(id, true);
        }
        
        mCheckedId = id;
		mProtectFromCheckedChange = false;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
    	List<RadioButton> btns = getAllRadioButton(child);
    	if(btns != null && btns.size() > 0){
    		for(RadioButton button : btns){
    			if (button.isChecked()) {
        			mProtectFromCheckedChange = true;
        			if (mCheckedId != -1) {
        				setCheckedStateForView(mCheckedId, false);
        			}
        			mProtectFromCheckedChange = false;
        			setCheckedId(button.getId());
        		}
    		}
    	}
        super.addView(child, index, params);
    }
    
    private List<RadioButton> getAllRadioButton(View child){
    	List<RadioButton> btns = new ArrayList<RadioButton>();
    	if (child instanceof RadioButton) {
    		btns.add((RadioButton) child);
    	}else if(child instanceof ViewGroup){
    		int counts = ((ViewGroup) child).getChildCount();
    		for(int i = 0; i < counts; i++){
    			btns.addAll(getAllRadioButton(((ViewGroup) child).getChildAt(i)));
    		}
    	}
    	return btns;
    }

    public void check(int id) {
        // don't even bother
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
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, mCheckedId);
        }
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
        return new MyRadioGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MyRadioGroup.LayoutParams;
    }

    @Override
    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(MyRadioGroup.class.getName());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(MyRadioGroup.class.getName());
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
        protected void setBaseAttributes(TypedArray a,
                int widthAttr, int heightAttr) {

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
        public void onCheckedChanged(MyRadioGroup group, int checkedId);
    }

    private class CheckedStateTracker implements CompoundButton.OnCheckedChangeListener {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
            OnHierarchyChangeListener {
        private OnHierarchyChangeListener mOnHierarchyChangeListener;

        @SuppressLint("NewApi")
		public void onChildViewAdded(View parent, View child) {
            if (parent == MyRadioGroup.this ) {
            	List<RadioButton> btns = getAllRadioButton(child);
            	if(btns != null && btns.size() > 0){
            		for(RadioButton btn : btns){
            			int id = btn.getId();
            			// generates an id if it's missing
            			if (id == View.NO_ID && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            				id = View.generateViewId();
            				btn.setId(id);
            			}
            			btn.setOnCheckedChangeListener(
            					mChildOnCheckedChangeListener);
            		}
            	}
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }

        /**
         * {@inheritDoc}
         */
        public void onChildViewRemoved(View parent, View child) {
            if (parent == MyRadioGroup.this) {
                List<RadioButton> btns = getAllRadioButton(child);
            	if(btns != null && btns.size() > 0){
            		for(RadioButton btn : btns){
            			btn.setOnCheckedChangeListener(null);
            		}
            	}
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }
}