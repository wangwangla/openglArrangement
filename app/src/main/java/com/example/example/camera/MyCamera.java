package com.example.example.camera;

import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;
import com.example.example.base.Filter;
import com.example.example.base.filter.OESFilter;

import javax.microedition.khronos.opengles.GL10;

public class MyCamera extends BaseDrawer {
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
    public void render() {
        instance.render();
       filter.render();

    }

    @Override
    public void surfaceChange(int width, int height) {
        filter.change(
                instance.getWidth(),
                instance.getHight(),
                width,
                height);
    }

    @Override
    public void dispose() {

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