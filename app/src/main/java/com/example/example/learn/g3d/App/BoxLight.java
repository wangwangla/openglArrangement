package com.example.example.learn.g3d.App;

import android.content.Context;

import com.example.example.base.BaseDrawer;
import com.example.example.base.filter.f3d.LightFilter;

import javax.microedition.khronos.opengles.GL10;

public class BoxLight implements BaseDrawer {
    private LightFilter filter;
    private GL10 gl10;
    private Context context;

    @Override
    public void create() {
        filter = new LightFilter();
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
