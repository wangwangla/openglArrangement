package com.example.example.hight;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.example.example.base.BaseDrawer;
import com.example.example.base.Filter;
import com.example.example.hight.filter.BaseFilter;
import com.example.example.hight.filter.BaseRender;
import com.example.example.hight.filter.BaseRenderBean;
import com.example.example.hight.filter.GaussianBlurFilter;
import com.example.example.hight.filter.RenderManager;
import com.example.example.hight.filter.RenderProcess;

import java.io.IOException;

public class HImageShow01 extends BaseDrawer {
    private Bitmap mBitmap;

    RenderProcess renderProcess;
    private int texture = -1;
    RenderManager instance;
    @Override
    public void create() {
        GaussianBlurBean blurBean = new GaussianBlurBean("高斯模糊", 3, 54, 5, 5);
        instance = RenderManager.getInstance(context);
        instance.setFilter(1, blurBean);

        instance.init(1);
//        renderProcess = new RenderProcess(context);
//        GaussianBlurBean blurBean
//                = new GaussianBlurBean("xx",39,1);
//        renderProcess.setFilter(blurBean);
//        renderProcess.init();
//        renderProcess.onCreate();
//        renderProcess.onChange();
//
//        texture = createTexture();
//        baseFilter.onCreate();
//        baseFilter.setContext(context);
//        baseFilter.setRenderBean();
        instance.onCreate(0);

    }

    @Override
    public void render() {

        instance.onDraw(0,texture,720,1280);
    }

    @Override
    public void surfaceChange(int width, int height) {
        GLES20.glViewport(0,0,width,height);
        instance.onChange(0);
    }

    @Override
    public void dispose() {

    }

    private int createTexture() {
        try {
            mBitmap = BitmapFactory.decodeStream(context.getAssets().open("text.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] texture = new int[1];
        if (mBitmap != null && !mBitmap.isRecycled()) {
            //生成纹理
            GLES20.glGenTextures(1, texture, 0);
            //绑定
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
//            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
//            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
//            根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);
            return texture[0];
        }
        return 0;
    }
}
