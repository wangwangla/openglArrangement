package com.example.example.filter.hight;

import android.opengl.GLES20;

import com.example.example.base.filter.Filter;

import javax.microedition.khronos.opengles.GL;

public class MixFilter extends Filter {
    protected int glHTexture1;
    private int tett = 0;

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

    public void setTexture1(int text){
        tett  =text;
    }

    @Override
    public void getLocation() {
        glHTexture1 = GLES20.glGetUniformLocation(mProgram, "vTexture1");
    }

    @Override
    public void addOtherRender() {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tett);
        GLES20.glUniform1i(glHTexture1, 1);
    }

    @Override
    public void change(int imageWidth, int imageHight, int width, int hight) {

    }
}
