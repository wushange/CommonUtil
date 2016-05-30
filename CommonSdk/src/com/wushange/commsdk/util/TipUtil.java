package com.wushange.commsdk.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wushange.commsdk.R;
import com.wushange.commsdk.customview.simpleTip.ToolTip;
import com.wushange.commsdk.customview.simpleTip.ToolTipView;
import com.wushange.commsdk.customview.tip.ShowTipsBuilder;
import com.wushange.commsdk.customview.tip.ShowTipsView;

import org.xutils.common.util.LogUtil;

public class TipUtil {

    public TipUtil() {
        super();
    }

    public static void showTip(Context context, View tipvIew, String title,
                               String tip) {
        ShowTipsView showtips = new ShowTipsBuilder(context).setTarget(tipvIew)
                .setTitle(title)
                .setDescription(tip)
                .setDelay(500)
                .setBackgroundAlpha(128)
                .setCircleColor(Color.parseColor("#00a3ff"))
                .setCloseButtonColor(Color.parseColor("#00000000"))
                .setCloseButtonTextColor(Color.WHITE)
                .build();
        showtips.show(context);
    }

    public static void showToolTipView(final Context context, View anchorView, int gravity, String text, int backgroundColor) {
        showToolTipView(context, anchorView, gravity, text, backgroundColor, 0L);
    }

    public static void showToolTipViewWithParent(final Context context, final Button anchorView, ViewGroup view, int gravity) {
        showToolTipView(context, anchorView, view, gravity,
                "Tool tip for " + anchorView.getText(), Color.BLACK, 0L);
    }

    public static void showToolTipView(final Context context, final View anchorView, int gravity, String text, int backgroundColor, long delay) {
        showToolTipView(context, anchorView, null, gravity, text, backgroundColor, delay);
    }

    public static void showToolTipView(final Context context, final View anchorView, ViewGroup parentView, int gravity,
                                       String text, int backgroundColor, long delay) {
        if (anchorView.getTag() != null) {
            ((ToolTipView) anchorView.getTag()).remove();
            anchorView.setTag(null);
            return;
        }

        ToolTip toolTip = createToolTip(context, text, backgroundColor);
        ToolTipView toolTipView = createToolTipView(context, toolTip, anchorView, parentView, gravity);
        if (delay > 0L) {
            toolTipView.showDelayed(delay);
        } else {
            LogUtil.e("显示tip");
            toolTipView.show();
        }
        anchorView.setTag(toolTipView);

        toolTipView.setOnToolTipClickedListener(new ToolTipView.OnToolTipClickedListener() {
            @Override
            public void onToolTipClicked(ToolTipView toolTipView) {
                anchorView.setTag(null);
            }
        });
    }

    public static ToolTip createToolTip(Context context, String text, int backgroundColor) {
        Resources resources = context.getResources();
        int padding = resources.getDimensionPixelSize(R.dimen.padding);
        int textSize = resources.getDimensionPixelSize(R.dimen.text_size);
        int radius = resources.getDimensionPixelSize(R.dimen.radius);
        return new ToolTip.Builder()
                .withText(text)
                .withTextColor(Color.WHITE)
                .withTextSize(textSize)
                .withBackgroundColor(backgroundColor)
                .withPadding(padding, padding, padding, padding)
                .withCornerRadius(radius)
                .build();
    }

    public static ToolTipView createToolTipView(Context context, ToolTip toolTip, View anchorView, ViewGroup parentView, int gravity) {
        return new ToolTipView.Builder(context)
                .withAnchor(anchorView)
                .withParent(parentView)
                .withToolTip(toolTip)
                .withGravity(gravity)
                .build();
    }
}
