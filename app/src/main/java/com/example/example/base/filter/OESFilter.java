package com.example.example.base.filter;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.example.example.base.Filter;
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
        utils.getCenterInsideMatrix(imageWidth,imageHight,width,hight);
    }
}
