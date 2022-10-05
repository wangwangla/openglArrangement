package com.example.example.filter.f2d;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.example.example.base.filter.Filter;
import com.example.example.utils.MatrixUtils;

public class OESFilter extends Filter {
    private int glHMatrix;
    public OESFilter(){
        super();
        utils = new MatrixUtils();
        vertexShaderCode =
                "attribute vec4 vPosition;" +
                        "uniform mat4 uMatrix;" + //位置
                        "attribute vec2 vCoordinate;\n" +    // 纹理
                        "varying vec2 aCoordinate;\n" +      //  传递纹理   片段着色器
                        "void main(){\n" +
                        "    gl_Position=vPosition * uMatrix;\n" +
                        "    aCoordinate=vCoordinate;\n" +
                        "}";
        fragmentShaderCode =
                "#extension GL_OES_EGL_image_external:require\n"+
                "precision mediump float;\n" +
                        "uniform samplerExternalOES  vTexture;\n" +
                        "varying vec2 aCoordinate;\n" +
                        "void main(){\n" +
                        "    vec4 nColor=texture2D(vTexture,aCoordinate);\n" +
                        "    gl_FragColor=nColor;" +
                        "}";
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void getLocation() {
        glHMatrix = GLES20.glGetUniformLocation(mProgram,"uMatrix");
    }

    @Override
    public void addOtherRender() {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture);
        if (utils != null) {
            GLES20.glUniformMatrix4fv(glHMatrix, 1, false, utils.getmMVPMatrix(), 0);
        }
    }

    @Override
    public void change(int imageWidth, int imageHight,int width,int hight) {
        utils.getCenterInsideMatrix2d(imageWidth,imageHight,width,hight);
    }

    //自己复写父类方法
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


    public void calculateMatrix(int cameraId){
        if(cameraId==1){
            utils.flip(true,false);
            utils.rotateZ(90);
        }else{
            utils.rotateZ(270);
        }
    }

}
