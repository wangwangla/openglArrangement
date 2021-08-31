package com.example.example.learn.function.moban;

import android.content.Context;
import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;
import com.example.example.learn.function.common.CommonShow;
import com.example.example.learn.shape.DrawTriangle02;

public class MoBanTest extends BaseDrawer {
    private CommonShow srcShow;
    private DrawTriangle02 drawTriangle02;

    public MoBanTest(){
        srcShow = new CommonShow("text");
        drawTriangle02 = new DrawTriangle02();
    }

    @Override
    public void create() {
        srcShow.create();
        drawTriangle02.create();
    }

    @Override
    public void render() {
//        srcShow.render();


        GLES20.glEnable(GLES20.GL_STENCIL_TEST);
        GLES20.glStencilOp(GLES20.GL_KEEP, GLES20.GL_KEEP, GLES20.GL_REPLACE);//第一次绘制的像素的模版值 0+1 = 1
        GLES20.glStencilFunc(GLES20.GL_ALWAYS, 1, 0xEF);
        drawTriangle02.render();
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
        drawTriangle02.surfaceChange(width,height);
    }

    @Override
    public void setContext(Context context) {
        srcShow.setContext(context);
        drawTriangle02.setContext(context);
    }

    @Override
    public void dispose() {

    }
}
