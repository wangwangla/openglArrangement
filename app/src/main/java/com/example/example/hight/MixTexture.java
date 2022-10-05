package com.example.example.hight;

import android.opengl.GLES20;

import com.example.example.base.filter.Filter;
import com.example.example.base.ImageBaseDrawer;
import com.example.example.filter.hight.MixFilter;

public class MixTexture extends ImageBaseDrawer {
    private MixFilter filter;

    public MixTexture() {
        filter = new MixFilter();
    }

    @Override
    public void create() {
        filter.create();
        filter.setTexture(createTexture());
        filter.setTexture1(createTexture("345.png"));
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
