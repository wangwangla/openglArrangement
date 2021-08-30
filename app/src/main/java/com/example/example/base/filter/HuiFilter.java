package com.example.example.base.filter;

public class HuiFilter extends MatrixFilter {
    public HuiFilter(){
        fragmentShaderCode =
                "precision mediump float;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "varying vec2 aCoordinate;\n" +
                        "void main(){\n" +
                        "     vec4 nColor=texture2D(vTexture,aCoordinate);\n" +
                        "     if(aCoordinate.x>0.5){"+
                        "           gl_FragColor=vec4(nColor.r,nColor.r,nColor.r,nColor.a);" +
                        "      }else{" +
                        "           gl_FragColor = nColor;       " +
                        "       }" +
                        "}";
    }
}
