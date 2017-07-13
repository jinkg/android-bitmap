/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.android.bitmap.sample;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.android.bitmap.BitmapCache;
import com.android.bitmap.DecodeAggregator;
import com.android.bitmap.UnrefedBitmapCache;
import com.android.bitmap.drawable.ExtendedBitmapDrawable;
import com.android.bitmap.drawable.ExtendedBitmapDrawable.ExtendedOptions;

public class MainActivity extends Activity {

    private ListView mListView;
    private final BitmapCache mCache = new UnrefedBitmapCache(TARGET_CACHE_SIZE_BYTES, 0, 0);
    private final DecodeAggregator mDecodeAggregator = new DecodeAggregator();

    private static Drawable PLACEHOLDER;
    private static Drawable PROGRESS;

    private static final float NORMAL_PARALLAX_MULTIPLIER = 1.5f;
    private static final int TARGET_CACHE_SIZE_BYTES = 5 * 1024 * 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (PLACEHOLDER == null) {
            Resources res = getResources();
            PLACEHOLDER = res.getDrawable(R.drawable.ic_placeholder);
            PROGRESS = res.getDrawable(R.drawable.progress);
        }

        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter {

        private final String[] mItems;

        private final String[] ITEMS = new String[]{
                "http://upload.art.ifeng.com/2015/0811/1439260959533.jpg",
                "http://img.zcool.cn/community/05e5e1554af04100000115a8236351.jpg",
                "http://tupian.enterdesk.com/2015/xu/04/20/12/zweihua11.jpg",
                "http://imgsrc.baidu.com/image/c0%3Dshijue%2C0%2C0%2C245%2C40/sign=b61c57bf06f431ada8df4b7a235fc6da/b58f8c5494eef01f3e82aae8eafe9925bc317d0c.jpg",
                "http://imgsrc.baidu.com/image/c0%3Dshijue%2C0%2C0%2C245%2C40/sign=79d198eedd2a6059461de959405d5eee/d1a20cf431adcbef5ed70a21a6af2edda3cc9f31.jpg",
                "http://image.elegantliving.ceconline.com/320000/320100/20110815_03_52.jpg",
                "http://imgsrc.baidu.com/image/c0%3Dshijue%2C0%2C0%2C245%2C40/sign=da6d2bd50046f21fdd3956109e4d0115/b3119313b07eca80689f5cc99b2397dda1448301.jpg",
                "http://img.sj33.cn/uploads/allimg/201005/2010050710300511.jpg",
                "http://imgsrc.baidu.com/image/c0%3Dshijue%2C0%2C0%2C245%2C40/sign=c4899f014b34970a537e186cfda3bbbd/1ad5ad6eddc451daed915bc5bcfd5266d01632bc.jpg",
                "http://img.article.pchome.net/01/63/62/41/pic_lib/s960x639/6s960x639.jpg"
        };

        private static final int COPIES = 50;

        public MyAdapter() {
            mItems = new String[ITEMS.length * COPIES];
            for (int i = 0; i < COPIES; i++) {
                for (int j = 0; j < ITEMS.length; j++) {
                    mItems[i * ITEMS.length + j] = ITEMS[j];
                }
            }
        }

        @Override
        public int getCount() {
            return mItems.length;
        }

        @Override
        public Object getItem(int position) {
            return mItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BitmapView v;
            if (convertView != null) {
                v = (BitmapView) convertView;
            } else {
                v = new BitmapView(MainActivity.this);
                ExtendedOptions opts = new ExtendedOptions(
                        ExtendedOptions.FEATURE_ORDERED_DISPLAY | ExtendedOptions.FEATURE_PARALLAX
                                | ExtendedOptions.FEATURE_STATE_CHANGES, PLACEHOLDER, PROGRESS);
                opts.decodeAggregator = mDecodeAggregator;
                opts.parallaxSpeedMultiplier = NORMAL_PARALLAX_MULTIPLIER;
                opts.backgroundColor = Color.LTGRAY;
                final ExtendedBitmapDrawable drawable = new ExtendedBitmapDrawable(getResources(),
                        mCache, true /* limit density */, opts);

                v.setTypedDrawable(drawable);
                v.setListView(mListView);
            }
            v.getTypedDrawable().bind(new BitmapRequestKeyImpl(mItems[position]));
            return v;
        }
    }
}
