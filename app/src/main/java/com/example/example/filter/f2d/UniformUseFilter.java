package com.example.example.filter.f2d;

import android.opengl.GLES20;

import com.example.example.base.filter.BaseFilter;

/**
 * uniform使用
 */
public class UniformUseFilter extends BaseFilter {
    private int mPositionHandle;
    private int mColorHandle;
    // 每个顶点四个字节
    private int vertexCount;

    public UniformUseFilter(){
        vertexShaderCode =
                "attribute vec4 vPosition;" +
                        "void main() {" +
                        "  gl_Position = vPosition;" +
                        "}";

        fragmentShaderCode =
                "precision mediump float;" +
                        "uniform vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
                        "}";

        triangleCoords = new float[]{
                0.5f,  0.5f, 0.0f, // top
                -0.5f, -0.5f, 0.0f, // bottom left
                0.5f, -0.5f, 0.0f  // bottom right
        };
        vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
        color = new float[]{ 1.0f, 1.0f, 1.0f, 1.0f };
    }

    @Override
    public void create() {
        super.create();
//        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
//        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        mPositionHandle = getGetAttribLocation("vPosition");
        mColorHandle = getGetUniformLocation("vColor");
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render() {
        super.render();
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
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        //绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
