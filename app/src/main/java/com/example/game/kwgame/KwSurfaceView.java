package com.example.game.kwgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.example.game.kwgame.render.KwRender;


public class KwSurfaceView extends GLSurfaceView {
    private KwRender render;
    private Context context;
    public KwSurfaceView(Context context) {
        this(context,null);
    }

    public KwSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init(){
        setEGLContextClientVersion(2);
        render = new KwRender(context);
        setRenderer(render);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public void onResume() {
        super.onResume();
        render.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        render.onPause();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        render.destroy();
    }

    public void setType(int itemid) {
        render.setType(itemid);
    }
}
