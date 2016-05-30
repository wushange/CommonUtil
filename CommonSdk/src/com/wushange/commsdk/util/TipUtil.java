package com.wushange.commsdk.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.wushange.commsdk.customview.tip.ShowTipsBuilder;
import com.wushange.commsdk.customview.tip.ShowTipsView;

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
}
