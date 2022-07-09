package com.example.example.learn.g3d.App;

import android.content.Context;
import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;
import com.example.example.filter.f3d.LightFilter;
import com.example.example.utils.MatrixUtils;

import javax.microedition.khronos.opengles.GL10;

/**
 * 加深度测试
 *
 * 一个完整的光照案例
 */
public class FinishShapeLight implements BaseDrawer {
    private LightFilter filter;
    private GL10 gl10;
    private Context context;

    @Override
    public void create() {
        filter = new LightFilter();
        filter.create();
        MatrixUtils utils = filter.getUtils();
        utils.rotateY(10);
    }

    @Override
    public void pause() {

    }

    @Override
    public void render() {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LESS);
        filter.render();
        MatrixUtils utils = filter.getUtils();
        utils.rotateX(1);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
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
