package com.android.bitmap.sample;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.android.bitmap.drawable.ExtendedBitmapDrawable;
import com.android.bitmap.view.BitmapDrawableImageView;

/**
 * @author jinyalin
 * @since 2017/7/13.
 */

public class ScrollBitmapView extends BitmapDrawableImageView {
    private final float mDensity;

    private ScrollView mScrollView;

    public ScrollBitmapView(Context c) {
        this(c, null);
    }

    public ScrollBitmapView(Context c, AttributeSet attrs) {
        super(c, attrs);
        mDensity = getResources().getDisplayMetrics().density;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return (int) (500 * mDensity);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, int oldw, int oldh) {
        ExtendedBitmapDrawable drawable = getTypedDrawable();
        drawable.setDecodeDimensions(w, h);
    }

    public void setScrollView(final ScrollView scrollView) {
        mScrollView = scrollView;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        ExtendedBitmapDrawable drawable = getTypedDrawable();
        float fraction = (float) (getBottom() - mScrollView.getScrollY() - getHeight() / 2)
                / (mScrollView.getHeight() + getHeight());
        drawable.setParallaxFraction(fraction);

        super.onDraw(canvas);
    }
}
