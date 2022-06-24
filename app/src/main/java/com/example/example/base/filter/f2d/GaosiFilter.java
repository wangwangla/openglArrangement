package com.example.example.base.filter.f2d;

public class GaosiFilter extends MatrixFilter {
    public GaosiFilter(){
        fragmentShaderCode =
                "precision mediump float;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "varying vec2 aCoordinate;\n" +
                        "void main(){\n" +

                        "vec4 color = vec4(0.0);" +
                        "int coreSize = 3;" +
                        "int halfsize = coreSize / 2;" +
                        "float texelOffset = 0.02;" +
                        "float kernel[9];" +
                        "kernel[6] = 1.0;" +
                        "kernel[7] = 1.0;" +
                        "kernel[8] = 1.0;" +
                        "kernel[3] = 1.0;" +
                        "kernel[4] = 4.0;" +
                        "kernel[5] = 1.0;" +
                        "kernel[0] = 1.0;" +
                        "kernel[1] = 1.0;" +
                        "kernel[2] = 1.0;" +
                        "int index = 0;" +
                        "for(int y = 0;y<coreSize;y++){" +
                        "   for(int x = 0;x<coreSize;x++){" +
                        "       vec4 currentColor = texture2D(vTexture,aCoordinate + " +
                        "vec2(float((-1+x))*texelOffset,float((-1+y))*texelOffset));" +
                        "       color += currentColor*kernel[index];" +
                        "       index++;" +
                        "   }" +
                        "}" +
                        "color /= 16.0;" +
                        "    gl_FragColor=color;" +
                        "}";
    }
}
