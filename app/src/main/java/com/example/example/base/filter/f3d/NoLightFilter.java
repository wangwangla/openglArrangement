package com.example.example.base.filter.f3d;

import android.opengl.GLES20;
import com.example.example.base.BaseFilter;
import com.example.example.base.Filter;
import com.example.example.utils.MatrixUtils;

//无光源
public class NoLightFilter extends Filter {
    protected int glProgramId;
    protected int glUMatrix;
    protected int glAPosition;
    protected int glUBaseColor;

    public NoLightFilter() {
        vertexShaderCode =
            "attribute vec4 aPosition;\n" +
            "uniform mat4 uMatrix;\n" +
            "uniform vec4 uBaseColor;\n" +
            "uniform vec3 uLightColor;\n" +
            "uniform float uAmbientStrength;\n" +
            "varying vec4 vColor;\n" +
            "void main(){\n" +
            "    gl_Position=uMatrix*aPosition;\n" +
            "    vColor=uBaseColor;\n" +
            "}";

        fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
                        "}";
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
        glAPosition = GLES20.glGetAttribLocation(glProgramId, "aPosition");
        glUMatrix = GLES20.glGetUniformLocation(glProgramId, "uMatrix");
        glUBaseColor = GLES20.glGetUniformLocation(glProgramId, "uBaseColor");
    }

    @Override
    public void getLocation() {

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
    public void change(int imageWidth, int imageHight, int width, int hight) {

    }

    @Override
    public void render() {
        utils.rotateY(0.1F);
        GLES20.glUseProgram(mProgram);
        vertexBuffer.position(0);
        //物体颜色
        GLES20.glUniform4f(glUBaseColor, 1.0f, 1.0f, 1.0f, 1.0f);
        //传入顶点信息
        GLES20.glEnableVertexAttribArray(glAPosition);
        GLES20.glVertexAttribPointer(glAPosition, 3, GLES20.GL_FLOAT, false, 6 * 4, vertexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, triangleCoords.length / 6);
        //禁止顶点数组的句柄
        GLES20.glUniformMatrix4fv(glUMatrix, 1, false, utils.getmMVPMatrix(), 0);
    }

    @Override
    public void addOtherRender() {

    }
}
