package com.example.example.base;

import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;

public abstract class Filter extends BaseDrawer {
    private int glHPosition;
    private int glHTexture;
    private int glHCoordinate;
    protected int texture;

    public Filter() {
        triangleCoords = new float[]{
                -1.0f, 1.0f,
                -1.0f, -1.0f,
                1.0f, 1.0f,
                1.0f, -1.0f
        };
        color = new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
        };
        vertexShaderCode =
                "attribute vec4 vPosition;\n" +      //位置
                        "attribute vec2 vCoordinate;\n" +    // 纹理
                        "varying vec2 aCoordinate;\n" +      //  传递纹理   片段着色器
                        "void main(){\n" +
                        "    gl_Position=vPosition;\n" +
                        "    aCoordinate=vCoordinate;\n" +
                        "}";
        fragmentShaderCode =
                "precision mediump float;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "varying vec2 aCoordinate;\n" +
                        "void main(){\n" +
                        "    vec4 nColor=texture2D(vTexture,aCoordinate);\n" +
                        "    gl_FragColor=nColor;" +
                        "}";
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }

    @Override
    public void create() {
        super.create();
        glHPosition = GLES20.glGetAttribLocation(mProgram, "vPosition");
        glHCoordinate = GLES20.glGetAttribLocation(mProgram, "vCoordinate");
        glHTexture = GLES20.glGetUniformLocation(mProgram, "vTexture");
        getLocation();
    }

    public abstract void getLocation() ;

    @Override
    public void render() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);
        GLES20.glEnableVertexAttribArray(glHPosition);
        GLES20.glEnableVertexAttribArray(glHCoordinate);
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
//        GLES20.glUniform1i(glHTexture, 0);
        GLES20.glVertexAttribPointer(glHPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glVertexAttribPointer(glHCoordinate, 2, GLES20.GL_FLOAT, false, 0, colorBuffer);
        addOtherRender();
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    public abstract void addOtherRender() ;

    @Override
    public void surfaceChange(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void dispose() {

    }

    public abstract void change(int imageWidth,int imageHight,int width,int hight);
}