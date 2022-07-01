package com.example.example.base;

import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;
import com.example.example.utils.MatrixUtils;

public abstract class Filter extends BaseFilter {
    protected int glHPosition;
    protected int glHTexture;
    protected int glHCoordinate;
    protected int texture;

    /**
     * 在libGdx中设置目标位置是直接给了position属性
     */
    public Filter() {
        utils = new MatrixUtils();

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

    public void setScale(float x,float y){
        utils.scale(x,y);
    }

    public void rotation(){
        utils.rotateX(10);
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
        GLES20.glUseProgram(mProgram);
        GLES20.glEnableVertexAttribArray(glHPosition);
        GLES20.glEnableVertexAttribArray(glHCoordinate);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        GLES20.glUniform1i(glHTexture, 0);

        GLES20.glVertexAttribPointer(glHPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glVertexAttribPointer(glHCoordinate, 2, GLES20.GL_FLOAT, false, 0, colorBuffer);
        addOtherRender();
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    public void render(int texture){
        GLES20.glUseProgram(mProgram);
        GLES20.glEnableVertexAttribArray(glHPosition);
        GLES20.glEnableVertexAttribArray(glHCoordinate);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        GLES20.glUniform1i(glHTexture, 0);
        GLES20.glVertexAttribPointer(glHPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glVertexAttribPointer(glHCoordinate, 2, GLES20.GL_FLOAT, false, 0, colorBuffer);
        addOtherRender();
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    public abstract void addOtherRender() ;

    @Override
    public void surfaceChange(int width, int height) {
        super.surfaceChange(width,height);

    }

    @Override
    public void dispose() {

    }

    public abstract void change(int imageWidth,int imageHight,int width,int hight);

    public int getTexture(){
        return texture;
    }

    public void lookAt(){

    }

    public void move(float x,float y,float z){
        utils.translate(x,y,z);
    }
}