package com.example.example.base.filter;

public class ScaleBigFilter extends MatrixFilter {
    public ScaleBigFilter(){
        fragmentShaderCode =
                "precision mediump float;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "varying vec2 aCoordinate;\n" +
                        "void modifyColor(vec4 color){" +
                        " color.r = max(min(color.r,1.0),0.0);" +
                        " color.g = max(min(color.g,1.0),0.0);" +
                        " color.b = max(min(color.b,1.0),0.0);" +
                        " color.a = max(min(color.a,1.0),0.0);" +
                        "}" +
                        "void main(){\n" +
                        "vec2 uv = aCoordinate;" +
                        "       if(uv.x <= 0.5){" +
                        "   uv.x =uv.x * 2.0;" +
                        "}else{" +
                        "   uv.x = (uv.x - 0.5)*2.0;" +
                        "}" +
                        "if(uv.y <= 0.5){" +
                        "   uv.y = uv.y * 2.0;" +
                        "}else{" +
                        "   uv.y = (uv.y - 0.5) * 2.0;" +
                        "}" +
                        "vec4 nnn;" +
                        "if((aCoordinate.x-0.5) * (aCoordinate.x-0.5) + (aCoordinate.y-0.5) * (aCoordinate.y-0.5) < 0.25){" +
                        "   nnn = texture2D(vTexture,vec2(uv.x/2.0 + 0.25, uv.y/2.0+0.25));" +
                        "}else{" +
                        "   nnn = texture2D(vTexture,uv);" +
                        "}" +
//                        "nnn.b = nnn.b + 0.1;"+
                        "modifyColor(nnn);" +
                        "gl_FragColor= nnn;" +
                        "}";
    }
}
