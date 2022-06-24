package com.example.example.base.filter.f2d;

/**
 * 基本就是這樣的一個套路不寫了
 */
public class JiugongGeFilter extends MatrixFilter{
    public JiugongGeFilter(){
        fragmentShaderCode =
                "precision mediump float;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "varying vec2 aCoordinate;\n" +
                        "void main(){\n" +
                            "vec2 uv = aCoordinate;" +
                        "   if(uv.x<0.3){" +
                        "       uv.x = uv.x*3.0;" +
                        "       vec4 nColor=texture2D(vTexture,uv);\n" +
                        "       gl_FragColor=nColor;" +
                        "   }else if(uv.x<0.6){" +
                        "       uv.x = (uv.x-0.3)*3.0;" +
                        "       vec4 nColor=texture2D(vTexture,uv);\n" +
                        "       gl_FragColor=nColor;" +
                        "   }else{" +
                        "       uv.x = (uv.x-0.6)*3.0;" +
                        "       vec4 nColor=texture2D(vTexture,uv);\n" +
                        "       gl_FragColor=nColor;" +
                        "   }" +
                        "}";
    }
}
