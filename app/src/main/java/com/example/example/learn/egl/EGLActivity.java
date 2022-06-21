package com.example.example.learn.egl;

import android.os.Bundle;

import com.example.example.learn.shape.UniformUse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.example.R;

public class EGLActivity extends AppCompatActivity {
    private EGLRender render;
    private UniformUse uniformUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egl);
        SurfaceView surfaceView = findViewById(R.id.egl_surface);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                render = new EGLRender(surfaceHolder) {
                    @Override
                    public void create() {
                        uniformUse = new UniformUse();
                        uniformUse.create();
                    }

                    @Override
                    public void surface() {
                        uniformUse.surfaceChange(7720,1280);
                    }

                    @Override
                    public void onFrame() {
                        uniformUse.render();
                    }

                    @Override
                    public void setSize(int i1, int i2) {

                    }

                    @Override
                    public void dispose() {

                    }
                };
                render.setEGLRener();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                if (render != null){
                    render.setSize(i1,i2);
                }
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                render.dispose();
            }
        });
    }
}
