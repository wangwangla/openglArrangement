package com.example.example.base;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;

import com.example.example.utils.MatrixUtils;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public interface BaseDrawer {
    void create();
    void pause();
    void render();
    void surfaceChange(int width,int height);
    void dispose();
    void resume();
    void setGL(GL10 gl10);
    void setContext(Context context);
}