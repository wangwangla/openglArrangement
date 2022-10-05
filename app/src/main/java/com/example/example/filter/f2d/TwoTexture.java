package com.example.example.filter.f2d;

import android.opengl.GLES20;

public class TwoTexture extends MatrixFilter{
    public TwoTexture(){
        fragmentShaderCode =
                "precision mediump float;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "uniform sampler2D vTexture1;\n" +
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

    @Override
    public void getLocation() {
        super.getLocation();
        glHTexture = GLES20.glGetUniformLocation(mProgram, "vTexture1");
    }

    private int texture2;
    public void setTexture2(int texture2){
        this.texture2 = texture2;
    }

    @Override
    public void renderOther() {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture2);
    }
}
