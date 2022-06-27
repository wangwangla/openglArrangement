package com.example.example.learn.shape;

import android.opengl.GLES20;

import com.example.example.base.ArrayBuffer;
import com.example.example.base.BaseDrawer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * 线索绘制
 */
public class BufferIndexUse extends ArrayBuffer implements BaseDrawer {
    private int mPositionHandle;
    private int mColorHandle;
    // 每个顶点四个字节
    private int vertexCount=0;
    private int [] element ;
    private IntBuffer indexBuffer;
    public BufferIndexUse(){
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

        element = new int[]{
                0,1,2
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
        indexBuffer = loadbuffer(element);
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
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vb[0]);
        GLES20.glVertexAttribPointer(
                mPositionHandle,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                0);
        //设置绘制三角形的颜色
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(
                mColorHandle,
                4,
                GLES20.GL_FLOAT,
                false,
                16,
                colorBuffer
        );

        //绘制三角形
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,3,GLES20.GL_UNSIGNED_INT,indexBuffer);
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);
    }
}
