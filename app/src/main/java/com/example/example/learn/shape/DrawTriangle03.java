package com.example.example.learn.shape;

import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;

/**
 * 使用属性传值
 */
public class DrawTriangle03 extends BaseDrawer{
        private int mPositionHandle;
        private int mColorHandle;
        // 每个顶点四个字节
        private int vertexCount=0;

   public DrawTriangle03(){
        vertexShaderCode =
                "attribute vec4 vPosition;" +
                        "attribute  vec4 aColor;" +
                        "varying vec4 vColor;" +
                        "void main() {" +
                        "  " +
                        "  gl_Position = vPosition;" +
                        "  vColor = aColor;" +
                        "}";

        fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
                        "}";
        triangleCoords = new float[]{
                0.5f,  0.5f, 0.0f,  1.0f, 1.0f, 1.0f,1.0F,// top
                -0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 1.0f,1.0F,// bottom left
                0.5f, -0.5f, 0.0f , 1.0f, 1.0f, 1.0f ,1.0F// bottom right
        };
        vertexCount = triangleCoords.length / (COORDS_PER_VERTEX+3);
        //三个顶点，需要指定3种颜色。
        color = new float[]{
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f
        };
    }

    @Override
    public void create() {
    super.create();
    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
    mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
    }

        @Override
        public void surfaceChange(int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

        @Override
        public void dispose() {

    }

        @Override
        public void render() {
        GLES20.glUseProgram(mProgram);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        vertexBuffer.position(0);
        //准备三角形的坐标数据
        GLES20.glVertexAttribPointer(
                mPositionHandle,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                28,
                vertexBuffer);
        //设置绘制三角形的颜色
            vertexBuffer.position(3);
            GLES20.glEnableVertexAttribArray(mColorHandle);
            GLES20.glVertexAttribPointer(
                    mColorHandle,
                    4,
                    GLES20.GL_FLOAT,
                    false,
                    28,
                    vertexBuffer
            );
//        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        //绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);
   }
}
