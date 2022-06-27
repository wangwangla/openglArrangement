package com.example.example.base.filter.f2d;

import android.opengl.GLES20;

import com.example.example.base.Filter;

/**
 * 显示位置可以通过外面   也可以通过里面进行，   这给是通过里面
 */

public class MatrixFilter extends Filter {
    private int glHMatrix;
    public MatrixFilter(){
        vertexShaderCode =
                "attribute vec4 vPosition;" + //位置
                        "uniform mat4 vMatrix;"+
                        "attribute vec2 vCoordinate;\n" +    // 纹理
                        "varying vec2 aCoordinate;\n" +      //  传递纹理   片段着色器
                        "void main(){\n" +
                        "    gl_Position=vPosition * vMatrix;\n" +
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

    @Override
    public void getLocation() {
        glHMatrix = GLES20.glGetUniformLocation(mProgram,"vMatrix");
    }


    @Override
    public void addOtherRender() {
        if (utils != null) {
            GLES20.glUniformMatrix4fv(glHMatrix, 1, false, utils.getmMVPMatrix(), 0);
        }
    }

    @Override
    public void change(int imageWidth, int imageHight,int width,int hight) {
        utils.getCenterInsideMatrix2d(imageWidth,imageHight,width,hight);
    }

    @Override
    public void lookAt() {

    }


}
