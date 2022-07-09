package com.example.example.filter.f2d;

import android.opengl.GLES20;

public class FboFilter extends MatrixFilter {
    int framebuffer[] = new int[1];
    int textur[] = new int[2];
    public FboFilter(){
        GLES20.glGenFramebuffers(1,framebuffer,0);

        //初始化纹理,一个绑定fbo  一个显示,
        GLES20.glGenTextures(textur.length, textur, 0);
        for (int texture : textur) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        }

    }

    @Override
    public void render() {
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,framebuffer[0]);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D,
                0,
                GLES20.GL_RGBA,
                1000,
                1000,
                0,
                GLES20.GL_RGBA,
                GLES20.GL_UNSIGNED_BYTE,
                null);

//
//
        GLES20.glUseProgram(mProgram);
        GLES20.glEnableVertexAttribArray(glHPosition);
        GLES20.glEnableVertexAttribArray(glHCoordinate);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textur[1]);
        GLES20.glUniform1i(glHTexture, 0);
        GLES20.glVertexAttribPointer(glHPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glVertexAttribPointer(glHCoordinate, 2, GLES20.GL_FLOAT, false, 0, colorBuffer);
        addOtherRender();
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glDisableVertexAttribArray(glHPosition);
        GLES20.glDisableVertexAttribArray(glHCoordinate);



        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,0);


    }

    public int getTextur() {
        return textur[0];
    }
}
