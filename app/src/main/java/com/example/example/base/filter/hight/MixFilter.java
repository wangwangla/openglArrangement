package com.example.example.base.filter.hight;

import android.opengl.GLES20;

import com.example.example.base.Filter;
import com.example.example.hight.MixTexture;

public class MixFilter extends Filter {
    protected int glHTexture1;
    public MixFilter(){
        fragmentShaderCode =
                "precision mediump float;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "uniform sampler2D vTexture1;\n" +
                        "varying vec2 aCoordinate;\n" +
                        "void main(){\n" +
                        "    vec4 nColor=mix(texture2D(vTexture, aCoordinate),texture2D(vTexture1, vec2(1.0 - aCoordinate.x, aCoordinate.y)), 0.2);\n" +
                        "    gl_FragColor=nColor;" +
                        "}";
    }

    @Override
    public void getLocation() {
        glHTexture1 = GLES20.glGetUniformLocation(mProgram, "vTexture1");
    }

    @Override
    public void addOtherRender() {
        GLES20.glUniform1i(glHTexture, 0);
    }

    @Override
    public void change(int imageWidth, int imageHight, int width, int hight) {

    }
}
