package com.example.example.learn.egl;


import android.annotation.SuppressLint;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class EGLHandleThread extends HandlerThread {
    private EGLConfig eglConfig = null;
    @SuppressLint("NewApi")
    private EGLDisplay eglDisplay = EGL14.EGL_NO_DISPLAY;
    @SuppressLint("NewApi")
    private EGLContext eglContext = EGL14.EGL_NO_CONTEXT;
    private int program;
    private int vPosition;
    private int uColor;
    private SurfaceHolder surfaceHolder;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("我爱你中国");
            isDraw = true;
            handler.sendEmptyMessageDelayed(0,5000);
        }
    };


    public EGLHandleThread(SurfaceHolder holder){
        super("EGL");
        this.surfaceHolder = holder;
    }

    @SuppressLint("NewApi")
    private void create(){
        //连接屏幕
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            eglDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
        }
        //初始化得到主次版本
        int []version = new int[2];
        if (!EGL14.eglInitialize(eglDisplay,version,0,version,1)){
            throw new RuntimeException("EGL error "+EGL14.eglGetError());
        }
        int []configAttribs = {
                EGL14.EGL_BUFFER_SIZE, 32,
                EGL14.EGL_ALPHA_SIZE, 8,
                EGL14.EGL_BLUE_SIZE, 8,
                EGL14.EGL_GREEN_SIZE, 8,
                EGL14.EGL_RED_SIZE, 8,
                EGL14.EGL_RENDERABLE_TYPE, //renderable
                EGL14.EGL_OPENGL_ES2_BIT,
                EGL14.EGL_SURFACE_TYPE,
                EGL14.EGL_WINDOW_BIT,
                EGL14.EGL_NONE
        };
        int [] numConfigs = new int[1];
        EGLConfig[] eglConfigs = new EGLConfig[1];
        //选择自己需要的屏幕数据配置，可以自己选择，也可以自动选择，这个是自动渲染，将属性给它们
        if (EGL14.eglChooseConfig(eglDisplay,
                configAttribs,
                0,
                eglConfigs,
                0,
                eglConfigs.length,
                numConfigs,
                0)){

        }
        eglConfig = eglConfigs[0];
        int[] contextAttribs = {
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 2,
                EGL14.EGL_NONE
        };
        eglContext = EGL14.eglCreateContext(eglDisplay, eglConfig, EGL14.EGL_NO_CONTEXT, contextAttribs, 0);
        if (eglContext == EGL14.EGL_NO_CONTEXT) {
            throw new RuntimeException("EGL error " + EGL14.eglGetError());
        }
    }

    EGLSurface eglSurface;
    @SuppressLint("NewApi")
    public void surfaceCreated(SurfaceHolder surface){
        final int[] surfaceAttribs = { EGL14.EGL_NONE };
        eglSurface = EGL14.eglCreateWindowSurface(eglDisplay, eglConfig, surface, surfaceAttribs, 0);
        EGL14.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext);
        GLES20.glViewport(0,0,400,400);
        // 设置clear color颜色RGBA(这里仅仅是设置清屏时GLES20.glClear()用的颜色值而不是执行清屏)
        GLES20.glClearColor(1.0f, 0, 0, 1.0f);
        isDraw = true;
    }

    private boolean isDraw;
    @SuppressLint("NewApi")
    @Override
    public synchronized void start() {
        super.start();
        new Handler(getLooper()).post(new Runnable() {
            @Override
            public void run() {
                create();
                surfaceCreated(surfaceHolder);
                listener.create();
                listener.surface();
                while (true){
                    if(isDraw) {
                        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
                        GLES20.glClearColor(1.0f, 0, 0, 1.0f);
                        listener.onFrame();
                        EGL14.eglSwapBuffers(eglDisplay, eglSurface);
                        EGL14.eglDestroySurface(eglDisplay, eglSurface);
                        isDraw = false;
                    }
                }
            }
        });
    }

    private EGLRender.EGLListener listener;

    public void setEGLListener(EGLRender.EGLListener listener) {
        this.listener = listener;
    }
}