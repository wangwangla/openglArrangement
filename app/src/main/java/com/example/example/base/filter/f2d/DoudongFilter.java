package com.example.example.base.filter.f2d;

import android.opengl.GLES20;

public class DoudongFilter extends MatrixFilter {
    private int time;
    public DoudongFilter(){
        fragmentShaderCode =
                "precision mediump float;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "varying vec2 aCoordinate;\n" +
                        "uniform float time;" +
                        "void main(){\n" +
                        " // 一次抖动滤镜的时长 0.7\n" +
                        "    float duration = 0.9;\n" +
                        "    // 放大图片上限\n" +
                        "    float maxScale = 1.1;\n" +
                        "    // 颜色偏移步长\n" +
                        "    float offset = 0.02;\n" +
                        "\n" +
                        "    // 进度[0,1]\n" +
                        "    float progress = mod(time, duration) / duration; // 0~1\n" +
                        "    // 颜色偏移值范围[0,0.02]\n" +
                        "    vec2 offsetCoords = vec2(offset, offset) * progress;\n" +
                        "    // 缩放范围[1.0-1.1];\n" +
                        "    float scale = 1.0 + (maxScale - 1.0) * progress;\n" +
                        "\n" +
                        "    // 放大纹理坐标.\n" +
                        "    vec2 ScaleTextureCoords = vec2(0.5, 0.5) + (aCoordinate - vec2(0.5, 0.5)) / scale;\n" +
                        "\n" +
                        "    // 获取3组颜色rgb\n" +
                        "    // 原始颜色+offsetCoords\n" +
                        "    vec4 maskR = texture2D(vTexture, ScaleTextureCoords + offsetCoords);\n" +
                        "    // 原始颜色-offsetCoords\n" +
                        "    vec4 maskB = texture2D(vTexture, ScaleTextureCoords - offsetCoords);\n" +
                        "    // 原始颜色\n" +
                        "    vec4 mask = texture2D(vTexture, ScaleTextureCoords);\n" +
                        "\n" +
                        "    // 从3组来获取颜色:\n" +
                        "    // maskR.r,mask.g,maskB.b 注意这3种颜色取值可以打乱或者随意发挥.不一定写死.只是效果会有不一样.大家可以试试.\n" +
                        "    // mask.a 获取原图的透明度\n" +
                        "    gl_FragColor = vec4(maskR.r, mask.g, maskB.b, mask.a);\n" +
                        "}" ;
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
