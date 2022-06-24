package com.example.example.learn.fbo;

import android.content.Context;
import android.opengl.GLES20;

import com.example.example.base.Filter;
import com.example.example.base.ImageBaseDrawer;
import com.example.example.base.filter.f2d.FboFilter;
import com.example.example.base.filter.f2d.FboFilter1;

import javax.microedition.khronos.opengles.GL10;

public class FBODemo extends ImageBaseDrawer {
    private FboFilter fboFilter;
    private Filter textureFilter;

    public FBODemo() {
        fboFilter = new FboFilter();
        textureFilter = new FboFilter1();
    }

    @Override
    public void create() {
        fboFilter.create();
        fboFilter.setTexture(createTexture());

        textureFilter.create();

    }

    @Override
    public void pause() {

    }

    @Override
    public void render() {
        fboFilter.render();
        textureFilter.render(fboFilter.getTextur());
    }

    @Override
    public void surfaceChange(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        fboFilter.change(
                mBitmap.getWidth(),
                mBitmap.getHeight(),
                width,
                height);
        textureFilter.change(
                mBitmap.getWidth(),
                mBitmap.getHeight(),
                width,
                height);
    }

    @Override
    public void dispose() {
        fboFilter.dispose();
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