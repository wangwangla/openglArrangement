package com.example.example.learn.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import com.example.example.base.ImageBaseDrawer;
import com.example.example.base.filter.Filter;
import com.example.example.filter.f2d.MatrixFilter;
import com.example.example.utils.MatrixUtils;


public class ImageMove extends ImageBaseDrawer {

    private Filter filter;
    public ImageMove() {
        filter = new MatrixFilter();
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
        MatrixUtils utils = filter.getUtils();
        utils.translate(0.6F,0,0);
    }

    @Override
    public void dispose() {

    }
}
