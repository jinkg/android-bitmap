package com.android.bitmap.sample;

import android.content.Context;

import com.android.bitmap.RequestKey;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author jinyalin
 * @since 2017/7/13.
 */

public class BitmapResKeyImpl implements RequestKey {
    private final Context context;
    public final String assetName;

    public BitmapResKeyImpl(Context context, String assetName) {
        this.context = context;
        this.assetName = assetName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof BitmapResKeyImpl)) {
            return false;
        }
        final BitmapResKeyImpl other = (BitmapResKeyImpl) o;
        return assetName.equals(other.assetName);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash += 31 * hash + assetName.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[");
        sb.append(assetName);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Cancelable createFileDescriptorFactoryAsync(final RequestKey key,
                                                       final Callback callback) {
        return null;
    }

    @Override
    public InputStream createInputStream() throws IOException {
        return context.getAssets().open(assetName);
    }

    @Override
    public boolean hasOrientationExif() throws IOException {
        return false;
    }
}
