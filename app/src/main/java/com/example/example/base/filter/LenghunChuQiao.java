package com.example.example.base.filter;

import android.opengl.GLES20;

public class LenghunChuQiao extends MatrixFilter{
    private int time;
    public LenghunChuQiao(){
        fragmentShaderCode = fragmentShaderCode =
                "precision mediump float;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "varying vec2 aCoordinate;\n" +
                        "uniform float time;" +
                        "void main(){\n" +
                        "    // 一次灵魂出窍效果的时长 0.7\n" +
                        "    float duration = 0.7;\n" +
                        "    // 透明度上限\n" +
                        "    float maxAlpha = 0.4;\n" +
                        "    // 放大图片上限\n" +
                        "    float maxScale = 1.8;\n" +
                        "\n" +
                        "    // 进度值[0,1]\n" +
                        "    float progress = mod(time, duration) / duration; // 0~1\n" +
                        "    // 透明度[0,0.4]\n" +
                        "    float alpha = maxAlpha * (1.0 - progress);\n" +
                        "    // 缩放比例[1.0,1.8]\n" +
                        "    float scale = 1.0 + (maxScale - 1.0) * progress;\n" +
                        "\n" +
                        "    // 放大纹理坐标\n" +
                        "    // 根据放大比例，得到放大纹理坐标 [0,0],[0,1],[1,1],[1,0]\n" +
                        "    float weakX = 0.5 + (aCoordinate.x - 0.5) / scale;\n" +
                        "    float weakY = 0.5 + (aCoordinate.y - 0.5) / scale;\n" +
                        "    // 放大纹理坐标\n" +
                        "    vec2 weakTextureCoords = vec2(weakX, weakY);\n" +
                        "\n" +
                        "    // 获取对应放大纹理坐标下的纹素(颜色值rgba)\n" +
                        "    vec4 weakMask = texture2D(vTexture, weakTextureCoords);\n" +
                        "\n" +
                        "    // 原始的纹理坐标下的纹素(颜色值rgba)\n" +
                        "    vec4 mask = texture2D(vTexture, aCoordinate);\n" +
                        "\n" +
                        "    // 颜色混合 默认颜色混合方程式 = mask * (1.0-alpha) + weakMask * alpha;\n" +
                        "    gl_FragColor = mask * (1.0 - alpha) + weakMask * alpha;" +

                        "}";
    }


    private float delta = 0;

    @Override
    public void addOtherRender() {
        delta +=0.01F;
        super.addOtherRender();
        GLES20.glUniform1f(time,delta+1);
    }

    @Override
    public void getLocation() {
        super.getLocation();
        time = GLES20.glGetUniformLocation(mProgram,"time");
    }
}
