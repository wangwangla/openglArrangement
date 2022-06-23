package com.example.example.learn.function.alpha;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.example.example.base.BaseDrawer;
import com.example.example.base.BaseFilter;
import com.example.example.base.Filter;
import com.example.example.base.filter.AlphaFilter;
import com.example.example.base.filter.CommonFIlter;
import com.example.example.base.filter.MatrixFilter;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

public class AlphaDemo implements BaseDrawer {
    private Filter filter;
    private Bitmap mBitmap;
    private Context context;
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
