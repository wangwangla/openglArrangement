package com.example.example.otherlearn.core.photo;


import android.content.Context;

import com.example.example.otherlearn.opengles.render.Renderer;
import com.example.example.otherlearn.opengles.render.base.BaseRender;

public class PhotoRender implements Renderer {
    private BaseRender output;

    private int textureId;

    public PhotoRender(Context context) {
        output = new BaseRender(context);
    }

    @Override
    public void onCreate() {
        output.onCreate();
    }

    @Override
    public void onChange(int width, int height) {
        output.onChange(width, height);
    }

    @Override
    public void onDraw() {
        output.onDraw(textureId);
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }
}
