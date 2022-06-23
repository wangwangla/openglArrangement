package com.example.example.learn.light;

import android.opengl.GLES20;

import com.example.example.base.BaseDrawer;
import com.example.example.base.BaseFilter;
import com.example.example.utils.MatrixUtils;

/**
 * 绘制出白的
 */
public class CureLisght1 extends BaseFilter implements BaseDrawer {
    private int mPositionHandle;
    private int mColorHandle;
    // 每个顶点四个字节
    private int vertexCount=0;
    private int vMatrix;

    public CureLisght1(){
        vertexShaderCode =
                "attribute vec4 vPosition;" +
                        "uniform mat4 vMatrix;"+
                        "attribute  vec4 aColor;" +
                        "varying vec4 vColor;" +
                        "void main() {" +
                        "  " +
                        "  gl_Position = vPosition * vMatrix;" +
                        "  vColor = aColor;" +
                        "}";

        fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vec4(1.0,1.0,1.0,1.0);" +
                        "}";
        triangleCoords = new float[]{
                -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                -0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,

                -0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                -0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,

                -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,

                0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
                0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
                0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
                0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
                0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
                0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,

                -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
                0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
                0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
                0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,

                -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
                0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
                0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
                0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
                -0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
                -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f
        };
        vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
        color = new float[]{ 1.0f, 1.0f, 1.0f, 1.0f };
    }

    @Override
    public void create() {
        super.create();
//    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glBindAttribLocation(mProgram,mPositionHandle,"vPosition");
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
        vMatrix = GLES20.glGetUniformLocation(mProgram,"vMatrix");
        utils = new MatrixUtils();
    }

    @Override
    public void surfaceChange(int width, int height) {
        GLES20.glViewport(0,0,width,height);
        utils.getCenterInsideMatrix2d(width,height,width,height);
        utils.rotateY(10);
        utils.rotateX(10);
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
                24,
                vertexBuffer);
        //设置绘制三角形的颜色
        vertexBuffer.position(3);
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(
                mColorHandle,
                4,
                GLES20.GL_FLOAT,
                false,
                16,
                colorBuffer
        );
        GLES20.glUniformMatrix4fv(vMatrix,1,false,utils.getmMVPMatrix(),0);
        //绘制三角形
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,triangleCoords.length/6);
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);
    }
}
