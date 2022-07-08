package com.example.example.base.filter.f3d;

import android.opengl.GLES20;

import com.example.example.base.BaseFilter;
import com.example.example.utils.MatrixUtils;

public class SunLightFilter extends BaseFilter {
    private int glProgramId;
    protected int glUMatrix;
    protected int glULightPosition;
    protected int glAPosition;
    protected int glANormal;
    protected int glUAmbientStrength;
    protected int glUDiffuseStrength;
    protected int glUSpecularStrength;
    protected int glUBaseColor;
    protected int glULightColor;
    protected int glTexCoords;
    protected int view;
    protected int projection;
    protected int mDiffuse;
    protected int mSpecular;
    protected int mShininess;
    protected int lDirection;
    protected int lAmbient;
    protected int lDiffuse;
    protected int lSpecular;
    protected int viewPos;

    float lx = 0f;
    float ly = 0.8f;
    float lz = -1f;

    public SunLightFilter() {
        vertexShaderCode = uRes("vertex.frag");
        fragmentShaderCode = uRes("frag.frag");
        triangleCoords = new float[]{
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,

                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,

                -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, 0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, 0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,

                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,

                -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,
                0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
                0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,

                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
                -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f
        };
        color = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    }

    @Override
    public void create() {
        super.create();
        utils = new MatrixUtils();
        glProgramId = mProgram;
        glAPosition = GLES20.glGetAttribLocation(glProgramId, "aPos");
        glANormal = GLES20.glGetAttribLocation(glProgramId, "aNormal");
        glUMatrix = GLES20.glGetUniformLocation(glProgramId, "model");
        glULightPosition = GLES20.glGetUniformLocation(glProgramId, "uLightPosition");
        glUAmbientStrength = GLES20.glGetUniformLocation(glProgramId, "uAmbientStrength");
        glUDiffuseStrength = GLES20.glGetUniformLocation(glProgramId, "uDiffuseStrength");
        glUSpecularStrength = GLES20.glGetUniformLocation(glProgramId, "uSpecularStrength");
        glULightColor = GLES20.glGetUniformLocation(glProgramId, "uLightColor");
        glUBaseColor = GLES20.glGetUniformLocation(glProgramId, "uBaseColor");
        glTexCoords = GLES20.glGetAttribLocation(glProgramId, "aTexCoords");
        view = GLES20.glGetUniformLocation(glProgramId,"view");
        projection = GLES20.glGetUniformLocation(glProgramId,"projection");
    }

    @Override
    public void surfaceChange(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        utils.getCenterInsideMatrix2d(width, height, width, height);
        utils.rotateX(10);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render() {

        GLES20.glUseProgram(mProgram);

        vertexBuffer.position(0);
        //准备三角形的坐标数据
        //环境光强度
        GLES20.glUniform1f(glUAmbientStrength, 0.5F);
        //漫反射光强度
        GLES20.glUniform1f(glUDiffuseStrength, 0.3F);
        //镜面光强度
        GLES20.glUniform1f(glUSpecularStrength, 0.8F);
        //光源颜色
        GLES20.glUniform3f(glULightColor, 1.0f, 0.0f, 0.0f);
        //物体颜色
        GLES20.glUniform4f(glUBaseColor, 1.0f, 1.0f, 1.0f, 1.0f);
        //光源位置
        GLES20.glUniform3f(glULightPosition, lx, ly, lz);
        //传入顶点信息
        GLES20.glEnableVertexAttribArray(glAPosition);
        GLES20.glVertexAttribPointer(glAPosition, 3, GLES20.GL_FLOAT, false, 6 * 4, vertexBuffer);
        //传入法线信息
        GLES20.glEnableVertexAttribArray(glANormal);
        vertexBuffer.position(3);
        GLES20.glVertexAttribPointer(glANormal, 3, GLES20.GL_FLOAT, false, 6 * 4, vertexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, triangleCoords.length / 6);
        //禁止顶点数组的句柄
        GLES20.glUniformMatrix4fv(glUMatrix, 1, false, utils.getmMVPMatrix(), 0);
    }
}
