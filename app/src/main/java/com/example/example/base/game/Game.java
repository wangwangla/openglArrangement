package com.example.example.base.game;

import android.content.Context;
import android.opengl.GLES20;

import com.example.example.base.ApplicationListener;
import com.example.example.base.BaseDrawer;
import com.example.example.camera.MyCamera;
import com.example.example.learn.function.alpha.AlphaDemo;
import com.example.example.learn.function.blend.BlendShow;
import com.example.example.learn.function.dep.DepTest;
import com.example.example.learn.function.moban.MoBanTest;
import com.example.example.learn.image.ImageShow;
import com.example.example.learn.image.ImageShow01;
import com.example.example.learn.image.ImageShow02;
import com.example.example.learn.image.ImageShow03;
import com.example.example.learn.image.ImageShow04;
import com.example.example.learn.image.ImageShow05;
import com.example.example.learn.image.ImageShow06;
import com.example.example.learn.image.ImageShow07;
import com.example.example.learn.image.ImageShow08;
import com.example.example.learn.image.ImageShow09;
import com.example.example.learn.image.ImageShow10;
import com.example.example.learn.image.ImageShow11;
import com.example.example.learn.image.ImageShow12;
import com.example.example.learn.image.ImageShow13;
import com.example.example.learn.image.ImageShow14;
import com.example.example.learn.image.ImageShow15;
import com.example.example.learn.image.ImageShow16;
import com.example.example.learn.light.CureLisght;
import com.example.example.learn.readpix.ReadPixDemo;
import com.example.example.learn.shape.DrawTriangle;
import com.example.example.learn.shape.DrawTriangle02;
import com.example.example.learn.shape.DrawTriangle03;
import com.example.example.learn.shape.DrawTriangle04;
import com.example.example.learn.shape.DrawTriangle05;

import javax.microedition.khronos.opengles.GL10;

public class Game implements ApplicationListener {
    private BaseDrawer drawer;
    private Class aClass;
    private Context context;
    public Game(Context context) {
        this.context = context;
    }

    @Override
    public void create(GL10 gl10) {
        try {
            drawer = (BaseDrawer) aClass.newInstance();
            drawer.setGL(gl10);
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
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//        GLES20.glClearColor(1,0,0,1);
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
        GLES20.glViewport(0,0,width,height);
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
                aClass = ImageShow03.class;
                break;
            case 9:
                aClass = MyCamera.class;
                break;
            case 10:
                aClass = ImageShow04.class;
                break;
            case 11:
                aClass = ImageShow05.class;
                break;
            case 12:
                aClass = ImageShow06.class;
                break;
            case 13:
                aClass = ImageShow07.class;
                break;
            case 14:
                aClass = ImageShow08.class;
                break;
            case 15:
                aClass = ImageShow09.class;
                break;
            case 16:
                aClass = ImageShow10.class;
                break;
            case 17:
                aClass = ImageShow11.class;
                break;
            case 18 :
                aClass = ImageShow11.class;
                break;
            case 19:
                aClass = ImageShow12.class;
                break;
            case 20:
                aClass = ImageShow13.class;
                break;
            case 21:
                aClass = ImageShow14.class;
                break;
            case 22:
                aClass = ImageShow15.class;
                break;
            case 23:
                aClass = ImageShow16.class;
                break;
            case 24:
                aClass = AlphaDemo.class;
                break;
            case 25:
                aClass = BlendShow.class;
                break;
            case 26:
                aClass = DepTest.class;
                break;
            case 27:
                aClass = MoBanTest.class;
                break;
            case 29:
                aClass = ReadPixDemo.class;
                break;
            case 30:
                aClass = ReadPixDemo.class;
                break;
            case 31:
                aClass = ReadPixDemo.class;
                break;
            case 32:
                aClass = ReadPixDemo.class;
                break;
            case 33:
                aClass = ReadPixDemo.class;
                break;
            case 34:
                aClass = ReadPixDemo.class;
                break;
            case 35:
                aClass = CureLisght.class;
                break;
            case 1000:
                aClass = CureLisght.class;
                break;
            case 1001:
                aClass = CureLisght.class;
                break;
            case 1002:
                aClass = CureLisght.class;
                break;

        }
    }
}
