package com.example.example.filter.f2d;

import android.opengl.GLES20;

public class ZhouqiFangdaFilter extends MatrixFilter {
    private float time ;
    private int timeHandler;

    public ZhouqiFangdaFilter(){
        vertexShaderCode =
                "attribute vec4 vPosition;\n" +      //位置
                        "attribute vec2 vCoordinate;\n" +    // 纹理
                        "varying vec2 aCoordinate;\n" +
                        "uniform float time;" +
                        "const float PI = 3.1415926;" +      //  传递纹理   片段着色器
                        "void main(){\n" +

                        " // 一次缩放效果时长 0.6\n" +
                        "    float duration = 0.6;\n" +
                        "    // 最大缩放幅度\n" +
                        "    float maxAmplitude = 0.3;\n" +
                        "\n" +
                        "    // 表示时间周期.范围[0.0~0.6];\n" +
                        "    float time = mod(time, duration);\n" +
                        "\n" +
                        "    // amplitude [1.0,1.3]\n" +
                        "    float amplitude = 1.0 + maxAmplitude * abs(sin(time * (PI / duration)));\n" +
                        "\n" +
                        "    // 顶点坐标x/y 分别乘以放大系数[1.0,1.3]\n" +
                        "    gl_Position = vec4(vPosition.x * amplitude, vPosition.y * amplitude, vPosition.zw);" +
                        "aCoordinate=vCoordinate;" +
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
        super.getLocation();
        timeHandler = GLES20.glGetUniformLocation(mProgram,"time");
    }

    @Override
    public void addOtherRender() {
        super.addOtherRender();
        time+=0.1F;
        GLES20.glUniform1f(timeHandler,time);
    }
}
