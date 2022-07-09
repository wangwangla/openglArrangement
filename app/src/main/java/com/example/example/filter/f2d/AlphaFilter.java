package com.example.example.filter.f2d;

/**
 * 测试的结果就是，符合条件的使用，不符合的扔掉
 */
public class AlphaFilter extends MatrixFilter {
    public AlphaFilter(){
        fragmentShaderCode =
                "precision mediump float;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "varying vec2 aCoordinate;\n" +
                        "void main(){\n" +
                        "    vec4 nColor=texture2D(vTexture,aCoordinate);\n" +
                        "      if(nColor.r < 0.99){" +
                        "       discard;     " +
                        "}else{" +
                        "    gl_FragColor=nColor;" +
                        "}" +
                        "}";
    }
}
