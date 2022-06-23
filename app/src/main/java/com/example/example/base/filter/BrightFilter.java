package com.example.example.base.filter;

import android.opengl.GLES20;

import com.example.example.base.Filter;
import com.example.example.utils.MatrixUtils;

public class BrightFilter extends Filter {
    private int glHMatrix;
    public BrightFilter(){
        utils = new MatrixUtils();
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
                        "   if(aCoordinate.x>0.5){" +
                        "    gl_FragColor.rgb = vec3(gl_FragColor.rgb + vec3(0.6 * (-0.5)));" +
                        "   }" +
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
}
