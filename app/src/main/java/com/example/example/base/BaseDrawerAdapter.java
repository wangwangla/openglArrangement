package com.example.example.base;

import android.content.Context;

import javax.microedition.khronos.opengles.GL10;

public class BaseDrawerAdapter implements BaseDrawer {
    protected Context context;
    protected GL10 gl10;
    @Override
    public void create() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void render() {

    }

    @Override
    public void surfaceChange(int width, int height) {

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
