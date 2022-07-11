package com.example.example.learn.g3d.App;

import android.content.Context;

import com.example.example.base.BaseDrawer;

import javax.microedition.khronos.opengles.GL10;

public class MultLight implements BaseDrawer {
    public FinishShapeLight light;
    public FinishShapeLight cube;
    public MultLight(){
        light = new FinishShapeLight();
        cube = new FinishShapeLight();
    }
    @Override
    public void create() {
        light.create();
        cube.create();

        light.getFilter().getUtils().translate(0,0.4f,1);
    }

    @Override
    public void pause() {
        light.pause();
        cube.pause();
    }

    @Override
    public void render() {
        light.render();
        cube.render();
    }

    @Override
    public void surfaceChange(int width, int height) {
        light.surfaceChange(width,height);
        cube.surfaceChange(width,height);
    }

    @Override
    public void dispose() {
        light.dispose();
        cube.dispose();
    }

    @Override
    public void resume() {
        light.resume();
        cube.resume();
    }

    @Override
    public void setGL(GL10 gl10) {

    }

    @Override
    public void setContext(Context context) {

    }
}
