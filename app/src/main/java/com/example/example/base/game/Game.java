package com.example.example.base.game;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.example.example.base.ApplicationListener;
import com.example.example.base.BaseDrawer;
import com.example.example.camera.MyCamera;
import com.example.example.hight.MixTexture;
import com.example.example.learn.fbo.FBODemo;
import com.example.example.learn.function.alpha.AlphaDemo;
import com.example.example.learn.function.blend.BlendShow;
import com.example.example.learn.function.dep.DepTest;
import com.example.example.learn.function.moban.MoBanTest;
import com.example.example.learn.g3d.App.FinishShapeLight;
import com.example.example.learn.g3d.App.ManFanSheLight;
import com.example.example.learn.g3d.App.TieTu3D;
import com.example.example.learn.g3d.App.TieTu3DLight;
import com.example.example.learn.g3d.App.MaterialLight;
import com.example.example.learn.g3d.App.NoLight;
import com.example.example.learn.g3d.App.PingXingLight;
import com.example.example.learn.g3d.App.SunLight;
import com.example.example.learn.image.ImageShow;
import com.example.example.learn.image.ImageMatriUse;
import com.example.example.learn.image.ImageScale;
import com.example.example.learn.image.ImageBright;
import com.example.example.learn.image.ImageCold;
import com.example.example.learn.image.ImageWarm;
import com.example.example.learn.image.ImageDipian;
import com.example.example.learn.image.ImageDoudong;
import com.example.example.learn.image.ImageNinePatchAndScale;
import com.example.example.learn.image.ImageFushi;
import com.example.example.learn.image.ImageGaosi;
import com.example.example.learn.image.ImageHui;
import com.example.example.learn.image.ImageLingHuiChuQiao;
import com.example.example.learn.image.ImageMaoci;
import com.example.example.learn.image.ImageHsvConvert;
import com.example.example.learn.image.ImageJiuGongGe;
import com.example.example.learn.image.ImageZhouQiFangDa;
import com.example.example.learn.readpix.ReadPixDemo;
import com.example.example.learn.shape.BufferIndexUse;
import com.example.example.learn.shape.AttribUse;
import com.example.example.learn.shape.AttribMultVUse;
import com.example.example.learn.shape.BindAttribUse;
import com.example.example.learn.shape.MatrixUse;

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
        GLES20.glClear(
                GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT|
                GLES20.GL_STENCIL_BUFFER_BIT);
        GLES20.glClearColor(1,1,1,1);
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
//                aClass = UniformUse.class;
//                aClass = BufferUse.class;
                aClass = BufferIndexUse.class;
                break;
            case 1:
                aClass = AttribUse.class;
                break;
            case 2:
                aClass = AttribMultVUse.class;
                break;
            case 3:
                aClass = BindAttribUse.class;
                break;
            case 4:
                aClass = MatrixUse.class;
                break;
            case 5:
                aClass = ImageShow.class;
                break;
            case 6:
                aClass = ImageMatriUse.class;
                break;
            case 7:
                aClass = ImageScale.class;
                break;
            case 8:
                aClass = ImageBright.class;
                break;
            case 9:
                aClass = MyCamera.class;
                break;
            case 10:
                aClass = ImageCold.class;
                break;
            case 11:
                aClass = ImageWarm.class;
                break;
            case 12:
                aClass = ImageDipian.class;
                break;
            case 13:
                aClass = ImageDoudong.class;
                break;
            case 14:
                aClass = ImageNinePatchAndScale.class;
                break;
            case 15:
                aClass = ImageFushi.class;
                break;
            case 16:
                aClass = ImageGaosi.class;
                break;
            case 17:
                aClass = ImageHui.class;
                break;
            case 18 :
                aClass = ImageHui.class;
                break;
            case 19:
                aClass = ImageLingHuiChuQiao.class;
                break;
            case 20:
                aClass = ImageMaoci.class;
                break;
            case 21:
                aClass = ImageHsvConvert.class;
                break;
            case 22:
                aClass = ImageJiuGongGe.class;
                break;
            case 23:
                aClass = ImageZhouQiFangDa.class;
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
//            case 30:
//                aClass = ReadPixDemo.class;
//                break;
//            case 31:
//                aClass = ReadPixDemo.class;
//                break;
//            case 32:
//                aClass = ReadPixDemo.class;
//                break;
//            case 30:
//                aClass = ReadPixDemo.class;
//                break;
            case 30:
                aClass = FBODemo.class;
                break;
            case 1000:
                aClass = FinishShapeLight.class;
                break;
            case 1001:
                aClass = NoLight.class;
                break;
            case 1002:
                aClass = PingXingLight.class;
                break;
            case 1003:
                aClass = ManFanSheLight.class;
                break;
            case 1004:
                aClass = TieTu3D.class;
                break;
            case 1005:
                aClass = MixTexture.class;
                break;
            case 1006:
                aClass = MaterialLight.class;
                break;
            case 1007:
                aClass = TieTu3D.class;
                break;
            case 1008:
                aClass = SunLight.class;
                break;
            case 1009:
                Log.d("kw","-------------------------1009");
                aClass = TieTu3DLight.class;
                break;
        }
    }
}
