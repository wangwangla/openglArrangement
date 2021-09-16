package com.example.example.base.filter;

import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;

public class MaociFilter extends MatrixFilter {
    public MaociFilter(){

        fragmentShaderCode = "precision mediump float;\n" +
                "uniform sampler2D vTexture;\n" +
                "varying vec2 aCoordinate;\n" +
                "uniform float time;" +
                "const float PI = 3.1415926;" +
                "float rand(float n) {\n" +
                "    //fract(x),返回x的小数部分数据\n" +
                "    return fract(sin(n) * 43758.5453123);\n" +
                "}" +
                "void main(){\n" +
                "    // 最大抖动\n" +
                "    float maxJitter = 0.06;\n" +
                "    // 一次毛刺滤镜的时长\n" +
                "    float duration = 0.3;\n" +
                "    // 红色颜色偏移量\n" +
                "    float colorROffset = 0.01;\n" +
                "    // 绿色颜色偏移量\n" +
                "    float colorBOffset = -0.025;\n" +
                "\n" +
                "    // 时间周期[0.0,0.6];\n" +
                "    float time1 = mod(time, duration * 2.0);\n" +
                "    // 振幅:[0,1];\n" +
                "    float amplitude = max(sin(time1 * (PI / duration)), 0.0);\n" +
                "\n" +
                "    // 像素随机偏移[-1,1]\n" +
                "    float jitter = rand(aCoordinate.y) * 2.0 - 1.0; // -1~1\n" +
                "\n" +
                "    // 是否要做偏移.\n" +
                "    bool needOffset = abs(jitter) < maxJitter * amplitude;\n" +
                "\n" +
                "    // 获取纹理X值.根据needOffset，来计算它X撕裂.\n" +
                "    // needOffset = YES，撕裂较大;\n" +
                "    // needOffset = NO，撕裂较小.\n" +
                "    float textureX = aCoordinate.x + (needOffset ? jitter : (jitter * amplitude * 0.006));\n" +
                "\n" +
                "    // 撕裂后的纹理坐标x,y\n" +
                "    vec2 textureCoords = vec2(textureX, aCoordinate.y);\n" +
                "\n" +
                "    // 颜色偏移3组颜色\n" +
                "    // 根据撕裂后获取的纹理颜色值\n" +
                "    vec4 mask = texture2D(vTexture, textureCoords);\n" +
                "    // 撕裂后的纹理颜色偏移\n" +
                "    vec4 maskR = texture2D(vTexture, textureCoords + vec2(colorROffset * amplitude, 0.0));\n" +
                "    // 撕裂后的纹理颜色偏移\n" +
                "    vec4 maskB = texture2D(vTexture, textureCoords + vec2(colorBOffset * amplitude, 0.0));\n" +
                "\n" +
                "    // 红色/蓝色部分发生撕裂.\n" +
                "    gl_FragColor = vec4(maskR.r, mask.g, maskB.b, mask.a);\n" +
                "}";
    }

    @Override
    public void addOtherRender() {
        delta += 0.01F;
        super.addOtherRender();
        GLES20.glUniform1f(time,delta+1);
    }

    private int time;
    private float delta = 0;
    @Override
    public void getLocation() {
        super.getLocation();
        time = GLES20.glGetUniformLocation(mProgram,"time");
    }
}
