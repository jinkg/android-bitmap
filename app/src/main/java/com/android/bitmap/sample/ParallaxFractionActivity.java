package com.android.bitmap.sample;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ScrollView;

import com.android.bitmap.BitmapCache;
import com.android.bitmap.DecodeAggregator;
import com.android.bitmap.UnrefedBitmapCache;
import com.android.bitmap.drawable.ExtendedBitmapDrawable;

/**
 * @author jinyalin
 * @since 2017/7/13.
 */

public class ParallaxFractionActivity extends Activity {
    private ScrollView mScrollView;
    private ScrollBitmapView mScrollBitmapView;

    private final BitmapCache mCache = new UnrefedBitmapCache(TARGET_CACHE_SIZE_BYTES, 0, 0);
    private final DecodeAggregator mDecodeAggregator = new DecodeAggregator();

    private static final float NORMAL_PARALLAX_MULTIPLIER = 2.0f;
    private static final int TARGET_CACHE_SIZE_BYTES = 5 * 1024 * 1024;

    private static Drawable PLACEHOLDER;
    private static Drawable PROGRESS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax_fraction);

        if (PLACEHOLDER == null) {
            Resources res = getResources();
            PLACEHOLDER = res.getDrawable(R.drawable.ic_placeholder);
            PROGRESS = res.getDrawable(R.drawable.progress);
        }

        mScrollView = (ScrollView) findViewById(R.id.scroll_view);
        mScrollBitmapView = (ScrollBitmapView) findViewById(R.id.scroll_bitmap_view);

        setupScrollBitmapView();
    }

    private void setupScrollBitmapView() {
        ExtendedBitmapDrawable.ExtendedOptions opts = new ExtendedBitmapDrawable.ExtendedOptions(
                ExtendedBitmapDrawable.ExtendedOptions.FEATURE_ORDERED_DISPLAY
                        | ExtendedBitmapDrawable.ExtendedOptions.FEATURE_PARALLAX
                        | ExtendedBitmapDrawable.ExtendedOptions.FEATURE_STATE_CHANGES,
                PLACEHOLDER, PROGRESS);
        opts.decodeAggregator = mDecodeAggregator;
        opts.parallaxSpeedMultiplier = NORMAL_PARALLAX_MULTIPLIER;
        opts.backgroundColor = Color.LTGRAY;
        final ExtendedBitmapDrawable drawable = new ExtendedBitmapDrawable(getResources(),
                mCache, true /* limit density */, opts);

        mScrollBitmapView.setTypedDrawable(drawable);
        mScrollBitmapView.setScrollView(mScrollView);

        mScrollBitmapView.getTypedDrawable().bind(new BitmapResKeyImpl(this, "demo2.jpg"));
    }
}
