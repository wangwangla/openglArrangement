package com.example.example.learn.shape;

import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;
import com.example.example.base.filter.BaseFilter;

/**
 * 使用属性传值
 *
 * 1.mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
 * 2.GLES20.glEnableVertexAttribArray(mColorHandle);
 *   GLES20.glVertexAttribPointer(mColorHandle,4,GLES20.GL_FLOAT,false,16,colorBuffer);
 *
 * 3.
 */
public class AttribUse extends BaseFilter implements BaseDrawer {
    private int mPositionHandle;
    private int mColorHandle;
    // 每个顶点四个字节
    private int vertexCount=0;

   public AttribUse(){
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
                0.5f,  0.5f, 0.0f, // top
                -0.5f, -0.5f, 0.0f, // bottom left
                0.5f, -0.5f, 0.0f  // bottom right
        };
        vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
        //三个顶点，需要指定3种颜色。
        color = new float[]{
                1.0f, 1.0f, 1.0f, 1.0f,
                0f, 1.0f, 1.0f, 1.0f,
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
        //准备三角形的坐标数据
        GLES20.glVertexAttribPointer(
                mPositionHandle,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer);
        //设置绘制三角形的颜色
            GLES20.glEnableVertexAttribArray(mColorHandle);
            GLES20.glVertexAttribPointer(
                    mColorHandle,
                    4,
                    GLES20.GL_FLOAT,
                    false,
                    16,
                    colorBuffer
            );
//        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        //绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);
   }
}
