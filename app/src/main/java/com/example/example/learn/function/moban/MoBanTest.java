package com.example.example.learn.function.moban;

import android.content.Context;
import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;
import com.example.example.learn.function.common.CommonShow;
import com.example.example.learn.shape.AttribUse;

import javax.microedition.khronos.opengles.GL10;

public class MoBanTest implements BaseDrawer {
    private CommonShow srcShow;
    private AttribUse attribUse;

    public MoBanTest(){
        srcShow = new CommonShow("text");
        attribUse = new AttribUse();
    }

    @Override
    public void create() {
        srcShow.create();
        attribUse.create();
    }

    @Override
    public void pause() {

    }

    @Override
    public void render() {
//        srcShow.render();


        GLES20.glEnable(GLES20.GL_STENCIL_TEST);
        GLES20.glStencilOp(GLES20.GL_KEEP, GLES20.GL_KEEP, GLES20.GL_REPLACE);//第一次绘制的像素的模版值 0+1 = 1
        GLES20.glStencilFunc(GLES20.GL_ALWAYS, 1, 0xEF);
        attribUse.render();
        GLES20.glStencilFunc(GLES20.GL_NOTEQUAL, 0x1, 0xFF);//等于1 通过测试 ,就是上次绘制的图 的范围 才通过测试。
        GLES20.glStencilOp(GLES20.GL_KEEP, GLES20.GL_KEEP, GLES20.GL_KEEP);//没有通过测试的，保留原来的，也就是保留上一次的值。
        srcShow.render();
//
        GLES20.glDisable(GLES20.GL_STENCIL_TEST);
    }

    @Override
    public void surfaceChange(int width, int height) {
        srcShow.surfaceChange(width,height);
        srcShow.getFilter().getUtils().scale(1,1);
        attribUse.surfaceChange(width,height);
    }

    @Override
    public void setContext(Context context) {
        srcShow.setContext(context);
        attribUse.setContext(context);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void setGL(GL10 gl10) {

    }
}
