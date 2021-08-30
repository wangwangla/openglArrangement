package com.example.example.base.filter;

import com.example.example.base.filter.MatrixFilter;

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
