package com.example.example.base.filter.f2d;

/**
 * 腐蚀效果   根据网上的案例，我选择的图片不对。
 */
public class FushiFilter extends MatrixFilter {
    public FushiFilter(){
        fragmentShaderCode =
                "precision mediump float;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "varying vec2 aCoordinate;\n" +
                        "void main(){\n" +
                        "   if(aCoordinate.x>0.5){" +
                        "       float block = 1000.0;\n" +
                        "       float delta = 1.0/block;\n" +
                        "       vec4 maxColor = vec4(-1.0);\n" +
                        "       for (int i = -1; i <= 1 ; i++) {\n" +
                        "          for (int j = -1; j <= 1; j++) {\n" +
                        "               float x = aCoordinate.x + float(i) * delta;\n" +
                        "               float y = aCoordinate.y + float(i) * delta;\n" +
                        "               maxColor = max(texture2D(vTexture, vec2(x, y)), maxColor);\n" +
                        "           }\n" +
                        "       }\n" +
                        "       gl_FragColor = maxColor;\n" +
                        "   }else{" +
                        "       gl_FragColor = texture2D(vTexture,aCoordinate); " +
                        "   }" +
                        "}";
    }
}
