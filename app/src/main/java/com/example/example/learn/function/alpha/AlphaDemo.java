package com.example.example.learn.function.alpha;

import android.content.Context;
import android.opengl.GLES20;

import com.example.example.base.Filter;
import com.example.example.base.ImageBaseDrawer;
import com.example.example.filter.f2d.AlphaFilter;

import javax.microedition.khronos.opengles.GL10;

public class AlphaDemo extends ImageBaseDrawer {
    private Filter filter;
    public AlphaDemo() {
        filter = new AlphaFilter();
    }

    @Override
    public void create() {
        filter.create();
        filter.setTexture(createTexture());
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
        GLES20.glViewport(0,0,width,height);
        filter.change(
                mBitmap.getWidth(),
                mBitmap.getHeight(),
                width,
                height);
    }

    @Override
    public void dispose() {
        filter.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void setGL(GL10 gl10) {

    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
