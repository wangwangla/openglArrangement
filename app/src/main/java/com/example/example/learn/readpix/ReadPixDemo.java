package com.example.example.learn.readpix;

import android.opengl.GLES20;

import com.example.example.base.filter.Filter;
import com.example.example.base.ImageBaseDrawer;
import com.example.example.filter.f2d.CommonFIlter;

import java.nio.IntBuffer;

public class ReadPixDemo extends ImageBaseDrawer {

    private Filter filter;

    public ReadPixDemo() {
        filter = new CommonFIlter();
    }

    @Override
    public void create() {
        filter.create();
        filter.setTexture(createTexture());
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

    }

    /**
     * 这个的处理就是将数据写出去，然后从屏幕上在读取回来。
     */
    public void save(){
        IntBuffer ib = IntBuffer.allocate(mBitmap.getWidth() * mBitmap.getHeight());
        gl10.glReadPixels(0,0,mBitmap.getWidth(),mBitmap.getHeight(),
                GLES20.GL_RGBA,
                GLES20.GL_UNSIGNED_BYTE,ib);

        int[] ia = ib.array();
        System.out.println(ia);
    }
}
