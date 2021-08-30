package com.example.example.base.filter;

import com.example.example.base.Filter;

public class DipianFilter extends MatrixFilter {
    public DipianFilter(){
        fragmentShaderCode =
            "precision mediump float;\n" +
            "uniform sampler2D vTexture;\n" +
            "varying vec2 aCoordinate;\n" +
            "void main(){\n" +
            "     vec4 nColor=texture2D(vTexture,aCoordinate);\n" +
            "     if(aCoordinate.x>0.5){" +
            "           gl_FragColor=vec4(vec3(1.0 - nColor.rgb), 1.0);" +
            "     }else{" +
            "           gl_FragColor=nColor;\n" +
            "      }" +
            "}";
}
}
