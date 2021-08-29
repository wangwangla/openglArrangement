package com.example.example.base.game;

import android.content.Context;
import android.opengl.GLES20;

import com.example.example.base.ApplicationListener;
import com.example.example.base.BaseDrawer;
import com.example.example.camera.MyCamera;
import com.example.example.learn.image.ImageShow;
import com.example.example.learn.image.ImageShow01;
import com.example.example.learn.image.ImageShow02;
import com.example.example.learn.shape.DrawTriangle;
import com.example.example.learn.shape.DrawTriangle02;
import com.example.example.learn.shape.DrawTriangle03;
import com.example.example.learn.shape.DrawTriangle04;
import com.example.example.learn.shape.DrawTriangle05;

public class Game implements ApplicationListener {
    private BaseDrawer drawer;
    private Class aClass;
    private Context context;
    public Game(Context context) {
        this.context = context;
    }

    @Override
    public void create() {
        try {
            drawer = (BaseDrawer) aClass.newInstance();
            drawer.setContext(context);
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
                aClass = DrawTriangle02.class;
                break;
            case 2:
                aClass = DrawTriangle03.class;
                break;
            case 3:
                aClass = DrawTriangle04.class;
                break;
            case 4:
                aClass = DrawTriangle05.class;
                break;
            case 5:
                aClass = ImageShow.class;
                break;
            case 6:
                aClass = ImageShow01.class;
                break;
            case 7:
                aClass = ImageShow02.class;
                break;
            case 8:
                aClass = MyCamera.class;
                break;
        }
    }
}
