package com.example.example.learn.g3d.App;

import android.content.Context;

import com.example.example.base.BaseDrawer;
import com.example.example.base.BaseFilter;

import javax.microedition.khronos.opengles.GL10;

public class Base3DDrawder implements BaseDrawer {
    protected BaseFilter filter;
    protected GL10 gl10;
    protected Context context;

    @Override
    public void create() {
        filter.create();
    }

    @Override
    public void pause() {

    }

    @Override
    public void render() {
        filter.render();
    }

    @Override
    public void surfaceChange(int width, int height) {
        filter.surfaceChange(width,height);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void setGL(GL10 gl10) {
        this.gl10 = gl10;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
