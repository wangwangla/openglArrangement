package com.example.example.camera;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.example.base.Constant;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class xxxxx extends GLSurfaceView {

    public xxxxx(Context context) {
        super(context);
    }

    private MyCamera camera;
    public xxxxx(final Context context, AttributeSet attrs) {
        super(context, attrs);
        Constant.view= this;
        setRenderer(new Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
                camera = new MyCamera();
                camera.create();
            }

            @Override
            public void onSurfaceChanged(GL10 gl10, int i, int i1) {
                camera.surfaceChange(i,i1);
            }

            @Override
            public void onDrawFrame(GL10 gl10) {
                camera.render();
            }
        });
    }
}
