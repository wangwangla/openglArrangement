package com.example.example.learn.pat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.example.example.base.BaseDrawer;
import com.example.example.base.BaseDrawerAdapter;
import com.example.example.base.filter.Filter;
import com.example.example.filter.f2d.LenghunChuQiao;
import com.example.example.filter.particle.ParticleFilter;

import java.io.IOException;

public class PDemo extends BaseDrawerAdapter {
    private Bitmap mBitmap;
    private Filter filter;

    public PDemo() {
        filter = new ParticleFilter();
    }

    @Override
    public void create() {
        filter.create();
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

    }
}
