package com.example.example.base.game;

import android.opengl.GLES20;

import com.example.example.base.ApplicationListener;

public class Game implements ApplicationListener {
    @Override
    public void create() {

    }

    @Override
    public void render() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1,0,0,1);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void surfaceChanage(int width, int height) {

    }
}
