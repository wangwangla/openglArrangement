package com.example.example.hight;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;
import com.example.example.base.BaseDrawerAdapter;
import com.example.example.base.Filter;
import com.example.example.base.ImageBaseDrawer;
import com.example.example.base.filter.f2d.CommonFIlter;
import com.example.example.base.filter.hight.MixFilter;

import javax.microedition.khronos.opengles.GL10;

public class MixTexture extends ImageBaseDrawer {
    private Filter filter;

    public MixTexture() {
        filter = new MixFilter();
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
    }

    @Override
    public void dispose() {
        filter.dispose();
    }

}
