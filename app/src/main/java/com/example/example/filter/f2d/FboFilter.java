package com.example.example.filter.f2d;

import android.opengl.GLES20;
import java.nio.IntBuffer;

/**
 * 1.创建framebuffer
 * 2.创建纹理
 * 3.绑定纹理
 * 4.绑定framebuffer
 * 5.framebuffer绑定纹理
 * 6.绘制
 * 7.解绑framebuffer
 * 8.解绑纹理
 *
 */
public class FboFilter extends MatrixFilter {
    int framebuffer[] = new int[1];
    int textur[] = new int[1];

    public FboFilter(){
////        1.生成  frameBuffer
        GLES20.glGenFramebuffers(1,framebuffer,0);
        //初始化纹理,一个绑定fbo
        GLES20.glGenTextures(textur.length, textur, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textur[0]);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB,
                1000, 1000,
                0, GLES20.GL_RGB,
                GLES20.GL_UNSIGNED_BYTE, null);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
    }

    @Override
    public void render() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textur[0]);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, framebuffer[0]);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, textur[0], 0);
        int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
        if(status == GLES20.GL_FRAMEBUFFER_COMPLETE) {
            super.render();
        }
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
    }

    public int getTextur() {
        return textur[0];
    }
}
