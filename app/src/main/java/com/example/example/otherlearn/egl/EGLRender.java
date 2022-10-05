package com.example.example.otherlearn.egl;
import android.annotation.SuppressLint;
import android.view.SurfaceHolder;

public abstract class EGLRender{
    public abstract void create();
    public abstract void surface();
    public abstract void onFrame();
    private SurfaceHolder holder;

    public EGLRender(SurfaceHolder holder){
        this.holder = holder;
    }

    @SuppressLint("NewApi")
    public void setEGLRener(){
        EGLHandleThread thread = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            thread = new EGLHandleThread(holder);
        }

        EGLListener listener = new EGLListener() {
            @Override
            public void create() {
                EGLRender.this.create();
            }

            @Override
            public void surface() {
                EGLRender.this.surface();
            }

            @Override
            public void onFrame() {
                EGLRender.this.onFrame();
            }
        };
        thread.setEGLListener(listener);
        thread.start();
    }

    public abstract void setSize(int i1, int i2);

    public abstract void dispose();

    interface EGLListener{
        public void create();
        public void surface();
        public void onFrame();
    }
}