package com.example.example.learn.shape;

import android.content.Context;
import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;
import com.example.example.base.BaseFilter;
import com.example.example.base.Filter;
import com.example.example.filter.f2d.UniformUseFilter;

import javax.microedition.khronos.opengles.GL10;

/**
 * 使用uniform传值
 * 1.    mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")；
 * 2.    GLES20.glUniform4fv(mColorHandle, 1, color, 0);
 */
public class UniformUse implements BaseDrawer {
    private BaseFilter filter;
    public UniformUse(){
        filter = new UniformUseFilter();
    }

    @Override
    public void create() {
        filter.create();
    }

    @Override
    public void pause() {
        filter.pause();
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
        filter.dispose();
    }

    @Override
    public void resume() {
        filter.resume();
    }

    @Override
    public void setGL(GL10 gl10) {

    }

    @Override
    public void setContext(Context context) {

    }
}