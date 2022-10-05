package com.example.example.filter.f2d;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL;

public class FboFilter extends MatrixFilter {
    int framebuffer[] = new int[1];
    int textur[] = new int[1];

    public FboFilter(){
////        1.生成
        GLES20.glGenFramebuffers(1,framebuffer,0);
        GLES20.glBindBuffer(GLES20.GL_FRAMEBUFFER,framebuffer[0]);

        //初始化纹理,一个绑定fbo  一个显示,
        GLES20.glGenTextures(textur.length, textur, 0);
        for (int texture : textur) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB,  1000,1000, 0, GLES20.GL_RGB,
                    GLES20.GL_UNSIGNED_BYTE, null);
        }
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D,
                textur[0], 0);


        int rbo[] = new int[1];
        GLES20.glGenRenderbuffers(1, rbo,0);
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, rbo[0]);
        GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16,
                1000, 1000);// use a single renderbuffer object for both a depth AND stencil buffer.
        GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT,
                GLES20.GL_RENDERBUFFER, rbo[0]);; // now actually attach it
        // now that we actually created the framebuffer and added all attachments we want to check if it is actually complete now

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
    }

    @Override
    public void render() {

        IntBuffer framebuffer = IntBuffer.allocate(1);
        IntBuffer depthRenderbuffer = IntBuffer.allocate(1);
        IntBuffer texture = IntBuffer.allocate(1);
        int texWidth = 480, texHeight = 480;
        IntBuffer maxRenderbufferSize = IntBuffer.allocate(1);
        GLES20.glGetIntegerv(GLES20.GL_MAX_RENDERBUFFER_SIZE, maxRenderbufferSize);
        // check if GL_MAX_RENDERBUFFER_SIZE is >= texWidth and texHeight
        if((maxRenderbufferSize.get(0) <= texWidth) ||
                (maxRenderbufferSize.get(0) <= texHeight))
        {
            // cannot use framebuffer objects as we need to create
            // a depth buffer as a renderbuffer object
            // return with appropriate error
        }
        // generate the framebuffer, renderbuffer, and texture object names
        GLES20.glGenFramebuffers(1, framebuffer);
        GLES20.glGenRenderbuffers(1, depthRenderbuffer);
        GLES20.glGenTextures(1, texture);
        // bind texture and load the texture mip-level 0
        // texels are RGB565
        // no texels need to be specified as we are going to draw into
        // the texture
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.get(0));
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, texWidth, texHeight,
                0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_SHORT_5_6_5, null);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        // bind renderbuffer and create a 16-bit depth buffer
        // width and height of renderbuffer = width and height of
        // the texture
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, depthRenderbuffer.get(0));
        GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16,
                texWidth, texHeight);
        // bind the framebuffer
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, framebuffer.get(0));
        // specify texture as color attachment
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, texture.get(0), 0);
        // specify depth_renderbufer as depth attachment
        GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT,
                GLES20.GL_RENDERBUFFER, depthRenderbuffer.get(0));
        // check for framebuffer complete
        int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
        if(status == GLES20.GL_FRAMEBUFFER_COMPLETE)
        {
            System.out.println("---------------------xxxxxxxxxxxxxxxxxxx");
            // render to texture using FBO
            GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

            // Do a complete rotation every 10 seconds.
            long time = SystemClock.uptimeMillis() % 10000L;
            float angleInDegrees = (360.0f / 10000.0f) * (2 * (int) time);

            xxx();


            // render to window system provided framebuffer
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

            // Do a complete rotation every 10 seconds.
            time = SystemClock.uptimeMillis() % 10000L;
            angleInDegrees = (360.0f / 10000.0f) * ((int) time);



        }

        // cleanup
        GLES20.glDeleteRenderbuffers(1, depthRenderbuffer);
        GLES20.glDeleteFramebuffers(1, framebuffer);
        GLES20.glDeleteTextures(1, texture);
    }

    private void xxx() {
        GLES20.glUseProgram(mProgram);
        GLES20.glEnableVertexAttribArray(glHPosition);
        GLES20.glEnableVertexAttribArray(glHCoordinate);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        GLES20.glUniform1i(glHTexture, 0);

        GLES20.glVertexAttribPointer(glHPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glVertexAttribPointer(glHCoordinate, 2, GLES20.GL_FLOAT, false, 0, colorBuffer);
        addOtherRender();
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    public int getTextur() {
        return textur[0];
    }
}
