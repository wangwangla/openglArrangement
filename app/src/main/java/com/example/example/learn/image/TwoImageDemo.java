package com.example.example.learn.image;

import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;
import com.example.example.base.BaseDrawerAdapter;
import com.example.example.base.ImageBaseDrawer;
import com.example.example.base.filter.Filter;
import com.example.example.filter.f2d.TwoTexture;
import com.example.example.filter.f2d.WarmFilter;

public class TwoImageDemo extends ImageBaseDrawer {
    private TwoTexture filter;

    public TwoImageDemo() {
        filter = new TwoTexture();
    }

    @Override
    public void create() {
        filter.create();
        filter.setTexture(createTexture());
        filter.setTexture2(createTexture());
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

