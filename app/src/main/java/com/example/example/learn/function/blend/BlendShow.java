package com.example.example.learn.function.blend;

import android.content.Context;
import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;
import com.example.example.learn.function.common.CommonShow;

public class BlendShow extends BaseDrawer {
    private CommonShow srcShow;
    private CommonShow dstShow;

    public BlendShow(){
        srcShow = new CommonShow("text");
        dstShow = new CommonShow("dst");
    }

    @Override
    public void create() {
        srcShow.create();
        dstShow.create();
    }

    @Override
    public void render() {
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_DST_COLOR);
        srcShow.render();
        dstShow.render();
    }

    @Override
    public void surfaceChange(int width, int height) {
        srcShow.surfaceChange(width,height);
        srcShow.getFilter().getUtils().scale(1,1);
        dstShow.surfaceChange(width,height);
        dstShow.getFilter().getUtils().scale(0.4F,0.4F);
    }

    @Override
    public void setContext(Context context) {
        srcShow.setContext(context);
        dstShow.setContext(context);
    }

    @Override
    public void dispose() {

    }
}
