package com.example.example.filter.f2d;

public class ColdFilter extends MatrixFilter{
    public ColdFilter(){
        fragmentShaderCode =
                "precision mediump float;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "varying vec2 aCoordinate;\n" +
                        "void modifyColor(vec4 color){\n" +
                        "    color.r=max(min(color.r,1.0),0.0);\n" +
                        "    color.g=max(min(color.g,1.0),0.0);\n" +
                        "    color.b=max(min(color.b,1.0),0.0);\n" +
                        "    color.a=max(min(color.a,1.0),0.0);\n" +
                        "}" +
                        "void main(){\n" +
                        "     vec4 nColor=texture2D(vTexture,aCoordinate);\n" +
                        "     if(aCoordinate.x>0.5){" +
                            "     vec3 u_ChangeColor = vec3(0, 0, 0.1);" +
                            "     vec4 deltaColor=nColor+vec4(u_ChangeColor,0.0);\n" +
                            "     modifyColor(deltaColor);\n" +
                            "     gl_FragColor=deltaColor;\n" +
                        "     }else{" +
                        "           gl_FragColor=nColor;\n" +
                        "       }" +
                        "}";
    }
}
