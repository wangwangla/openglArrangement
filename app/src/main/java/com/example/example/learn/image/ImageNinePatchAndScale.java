package com.example.example.learn.image;

import android.opengl.GLES20;

import com.example.example.base.Filter;
import com.example.example.base.ImageBaseDrawer;
import com.example.example.filter.f2d.ScaleBigFilter;

/**
 * 抖动效果
 */
public class ImageNinePatchAndScale extends ImageBaseDrawer {
    private Filter filter;

    public ImageNinePatchAndScale() {
        filter = new ScaleBigFilter();
    }

    @Override
    public void create() {
        filter.create();
        filter.setTexture(createTexture());
    }

    @Override
    public void render() {
        filter.render();
    }

    @Override
    public void surfaceChange(int width, int height) {
        GLES20.glViewport(0,0,width,height);
        filter.change(
                mBitmap.getWidth(),
                mBitmap.getHeight(),
                width,
                height);
    }

    @Override
    public void dispose() {

    }
}
