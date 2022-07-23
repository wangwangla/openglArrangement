package com.example.example.filter.f2d;

public class HsvFilter extends MatrixFilter {
    public HsvFilter(){
        fragmentShaderCode =
                "precision mediump float;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "varying vec2 aCoordinate;\n" +
                        "\n" +
                        "vec3 rgb2hsv(vec3 c) {\n" +
                        "    const vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);\n" +
                        "    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));\n" +
                        "    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));\n" +
                        "\n" +
                        "    float d = q.x - min(q.w, q.y);\n" +
                        "    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + 0.001)), d / (q.x + 0.001), q.x);\n" +
                        "}" +
                        "vec3 hsv2rgb(vec3 c) {\n" +
                        "    const vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);\n" +
                        "    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);\n" +
                        "    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);\n" +
                        "}" +
                        "void main(){\n" +
                        "vec2 p = aCoordinate.xy;" +
                        "vec3 a = rgb2hsv(texture2D(vTexture, p).rgb);" +
                        //值的范围是0~1
                        "   vec3 m = a;" +
//                        "   m.x=m.x+0.2;" +
                        "gl_FragColor = vec4(hsv2rgb(m), 1.0);" +
                        "}";
    }
}
