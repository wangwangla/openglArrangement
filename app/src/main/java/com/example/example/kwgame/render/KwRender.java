package com.example.example.kwgame.render;

import android.opengl.GLSurfaceView;

import com.example.example.base.ApplicationListener;
import com.example.example.base.game.Game;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class KwRender implements GLSurfaceView.Renderer {
    private ApplicationListener listener;
    public KwRender(){
        listener = new Game();
    }
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        listener.create();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        listener.surfaceChanage(i,i1);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        listener.render();
    }

    public void onPause() {
        listener.pause();
    }

    public void onResume() {
        listener.resume();
    }

    public void destroy() {
        listener.dispose();
    }
}
