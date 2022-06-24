package com.example.example.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;
import com.example.example.base.Filter;
import com.example.example.base.filter.f2d.OESFilter;

import javax.microedition.khronos.opengles.GL10;

public class MyCamera implements BaseDrawer {
    private Filter filter;
    private CameraInstance instance;
    public MyCamera(){
        filter = new OESFilter();
        instance = new CameraInstance();
    }

    @Override
    public void create() {
        filter.create();
        int textureID = createTextureID();
        filter.setTexture(textureID);
        instance.open(1);
        instance.setPreviewTexture(new SurfaceTexture(textureID));
        instance.start();
    }

    @Override
    public void pause() {

    }

    @Override
    public void render() {

        instance.render();
       filter.render();

    }

    @Override
    public void surfaceChange(int width, int height) {
        filter.change(
                instance.getHight(),
                instance.getWidth(),
                width,
                height);
        ((OESFilter)(filter)).calculateMatrix(1);
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

    @Override
    public void setContext(Context context) {

    }

    private int createTextureID(){
            int[] texture = new int[1];
            GLES20.glGenTextures(1, texture, 0);
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                    GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR);
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                    GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                    GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                    GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
            return texture[0];
        }
}
