package com.example.example.learn.function.dep;

import android.content.Context;
import android.opengl.GLES20;
import com.example.example.base.BaseDrawer;
import com.example.example.learn.function.common.CommonShow;

/**
 * 距离相机越远的， 深度值越大
 *
 * 一般的是没有开启深度测试的，默认情况下， 先绘制近处的物体，然后绘制远处的，当近处的出现重叠的时候导致被远处的遮挡。
 * 先绘制的会挡住后绘制的    即使他的z值比较大
 * 开启模板测试的时候，会进行深度比较，在绘制的时候，深度大的在后，深度小的在前
 *
 */
public class DepTest extends BaseDrawer {
    private CommonShow srcShow;
    private CommonShow dstShow;

    public DepTest(){
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
        GLES20.glEnable(GLES20.GL_STENCIL_TEST);
        GLES20.glStencilFunc(GLES20.GL_ALWAYS, 1, 0xFF); //所有片段都要写入模板缓冲
        GLES20.glStencilOp(GLES20.GL_KEEP, GLES20.GL_KEEP, GLES20.GL_REPLACE);//若模板测试和深度测试都通过了，将片段对应的模板值替换为1
        GLES20.glStencilMask(0xFF);
        dstShow.render();
        GLES20.glStencilFunc(GLES20.GL_NOTEQUAL, 1, 0xFF);//当片段的模板值不为 1 时，片段通过测试进行渲染
        //禁用模板写入和深度测试
        GLES20.glStencilMask(0x00);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
         srcShow.render();
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
