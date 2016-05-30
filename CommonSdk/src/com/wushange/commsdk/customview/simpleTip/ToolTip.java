/*
 * Copyright (C) 2015 Xizhi Zhu
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
 */

package com.wushange.commsdk.customview.simpleTip;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.Gravity;

public class ToolTip {
    @StringRes
    private final int textResourceId;
    @Nullable
    private final CharSequence text;
    private final int textGravity;
    private final int textColor;
    private final float textSize;
    private final Typeface typeface;
    private final int typefaceStyle;
    private final int backgroundColor;
    private final int leftPadding;
    private final int rightPadding;
    private final int topPadding;
    private final int bottomPadding;
    private final float radius;

    private ToolTip(@StringRes int textResourceId, @Nullable CharSequence text, int textGravity,
                    int textColor, float textSize, Typeface typeface, int typefaceStyle,
                    int backgroundColor, int leftPadding, int rightPadding, int topPadding,
                    int bottomPadding, float radius) {
        this.textResourceId = textResourceId;
        this.text = text;
        this.textGravity = textGravity;
        this.textColor = textColor;
        this.textSize = textSize;
        this.typeface = typeface;
        this.typefaceStyle = typefaceStyle;
        this.backgroundColor = backgroundColor;
        this.leftPadding = leftPadding;
        this.rightPadding = rightPadding;
        this.topPadding = topPadding;
        this.bottomPadding = bottomPadding;
        this.radius = radius;
    }

    @StringRes
    public int getTextResourceId() {
        return textResourceId;
    }

    @Nullable
    public CharSequence getText() {
        return text;
    }

    public int getTextGravity() {
        return textGravity;
    }

    @ColorInt
    public int getTextColor() {
        return textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    @NonNull
    public Typeface getTypeface() {
        return typeface;
    }

    public int getTypefaceStyle() {
        return typefaceStyle;
    }

    @ColorInt
    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getLeftPadding() {
        return leftPadding;
    }

    public int getRightPadding() {
        return rightPadding;
    }

    public int getTopPadding() {
        return topPadding;
    }

    public int getBottomPadding() {
        return bottomPadding;
    }

    public float getCornerRadius() {
        return radius;
    }

    public static class Builder {
        @StringRes
        private int textResourceId = 0;
        private CharSequence text;
        private int textGravity = Gravity.NO_GRAVITY;
        private int textColor = Color.WHITE;
        private float textSize = 13.0F;
        private Typeface typeface = Typeface.DEFAULT;
        private int typefaceStyle = Typeface.NORMAL;
        private int backgroundColor = Color.BLACK;
        private int leftPadding = 0;
        private int rightPadding = 0;
        private int topPadding = 0;
        private int bottomPadding = 0;
        private float radius = 0.0F;

        public Builder() {
        }

        public Builder withText(@StringRes int text) {
            this.textResourceId = text;
            return this;
        }

        public Builder withText(CharSequence text) {
            this.text = text;
            return this;
        }

        public Builder withTextGravity(int gravity) {
            this.textGravity = gravity;
            return this;
        }

        public Builder withTextColor(@ColorInt int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder withTextSize(float textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder withTypeface(Typeface typeface) {
            if (typeface != null) {
                this.typeface = typeface;
            }
            return this;
        }

        public Builder withTypefaceStyle(int style) {
            this.typefaceStyle = style;
            return this;
        }

        public Builder withBackgroundColor(@ColorInt int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder withPadding(int leftPadding, int rightPadding, int topPadding, int bottomPadding) {
            this.leftPadding = leftPadding;
            this.rightPadding = rightPadding;
            this.topPadding = topPadding;
            this.bottomPadding = bottomPadding;
            return this;
        }

        public Builder withCornerRadius(float radius) {
            this.radius = radius;
            return this;
        }

        public ToolTip build() {
            return new ToolTip(textResourceId, text, textGravity, textColor, textSize, typeface,
                    typefaceStyle, backgroundColor, leftPadding, rightPadding, topPadding,
                    bottomPadding, radius);
        }
    }
}
