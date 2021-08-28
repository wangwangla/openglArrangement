package com.example.example.base.game;

import android.opengl.GLES20;

import com.example.example.base.ApplicationListener;
import com.example.example.base.BaseDrawer;
import com.example.example.learn.DrawTriangle;

public class Game implements ApplicationListener {
    private BaseDrawer drawer;
    private Class aClass;
    @Override
    public void create() {
        try {
            drawer = (BaseDrawer) aClass.newInstance();
            drawer.create();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1,0,0,1);
        drawer.render();
    }

    @Override
    public void pause() {
        drawer.pause();
    }

    @Override
    public void resume() {
        drawer.resume();
    }

    @Override
    public void dispose() {
        drawer.dispose();
    }

    @Override
    public void surfaceChanage(int width, int height) {
        drawer.surfaceChange(width,height);
    }

    @Override
    public void chageType(int itemid1) {
        switch (itemid1){
            case 0:
                aClass = DrawTriangle.class;
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }
}
